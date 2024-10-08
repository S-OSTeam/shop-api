package sosteam.deamhome.domain.naver.service

import kotlinx.coroutines.reactive.awaitSingle
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import sosteam.deamhome.domain.naver.dto.response.NaverTokenReturnResponse
import sosteam.deamhome.domain.naver.dto.response.NaverUnlinkResponse
import sosteam.deamhome.domain.naver.dto.response.NaverUserInfoResponse
import sosteam.deamhome.domain.naver.exception.NaverTokenNotFoundException
import sosteam.deamhome.domain.naver.exception.NaverUserNotFoundException
import sosteam.deamhome.global.provider.log

@Service
@Slf4j
@RequiredArgsConstructor
class NaverService(
	@Value("\${spring.security.oauth2.client.naver.client_id}")
	private val clientId: String,
	@Value("\${spring.security.oauth2.client.naver.client_secret}")
	private val clientSecret: String,
	@Value("\${spring.security.oauth2.client.naver.redirect_uri}")
	private val naverRedirectUri: String,
	@Value("\${spring.security.oauth2.client.naver.state}")
	private val naverState: String
) {
	suspend fun getNaverLoginPage(): String {
		val naverOAuth = "https://nid.naver.com/oauth2.0/authorize"
		val url = UriComponentsBuilder.fromUriString(naverOAuth)
			.queryParam("client_id", clientId).queryParam("response_type", "code")
			.queryParam("redirect_uri", naverRedirectUri).queryParam("state", naverState).build()
		
		return url.toUriString()
	}
	
	suspend fun getNaverToken(naverCode: String): String {
		log().info(getNaverLoginPage())
		val naverOAuth = "https://nid.naver.com"
		val naverPath = "/oauth2.0/token"
		val webclient = WebClient.builder().baseUrl(naverOAuth)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE).build()
		
		val response = webclient.get().uri { uriBuilder ->
			uriBuilder.path(naverPath)
				.queryParam("client_id", clientId)
				.queryParam("client_secret", clientSecret)
				.queryParam("grant_type", "authorization_code")
				.queryParam("state", naverState)
				.queryParam("code", naverCode).build()
		}.retrieve()
			.bodyToMono(NaverTokenReturnResponse::class.java)
			.awaitSingle()
		
		if (response == null || response.accessToken.isNullOrEmpty()) {
			throw NaverTokenNotFoundException()
		}
		
		return response.accessToken
	}
	
	suspend fun getNaverUserId(naverToken: String): String {
		val naverOAuth = "https://openapi.naver.com"
		val naverPath = "/v1/nid/me"
		val webclient = WebClient.builder().baseUrl(naverOAuth)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE).build()
		
		log().debug(naverToken)
		
		val response = webclient.get().uri { uriBuilder ->
			uriBuilder
				.path(naverPath)
				.build()
		}
			.header("Authorization", "Bearer $naverToken").retrieve()
			.bodyToMono(NaverUserInfoResponse::class.java)
			.awaitSingle()
		
		response.info?.id ?: throw NaverUserNotFoundException()
		
		return response.info.id
	}
	
	suspend fun unlinkNaver(snsCode: String): NaverUnlinkResponse {
		val naverOAuth = "https://nid.naver.com"
		val naverPath = "/oauth2.0/token"
		val webclient = WebClient.builder().baseUrl(naverOAuth)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build()
		
		val response = webclient.post().uri { uriBuilder ->
			uriBuilder.path(naverPath).queryParam("client_id", clientId)
				.queryParam("client_secret", clientSecret).queryParam("grant_type", "delete")
				.queryParam("access_token", snsCode).queryParam("service_provider", "NAVER").build()
		}.retrieve().onStatus({ status -> status.value() != 200 }) {
			throw NaverTokenNotFoundException()
		}.bodyToMono(NaverUnlinkResponse::class.java).awaitSingle()
		
		return response
	}
}