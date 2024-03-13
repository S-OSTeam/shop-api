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
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.exception.SnsIdNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountStatusValidService
import sosteam.deamhome.domain.naver.dto.response.NaverTokenReturnResponse
import sosteam.deamhome.domain.naver.dto.response.NaverUnlinkResponse
import sosteam.deamhome.domain.naver.dto.response.NaverUserInfo
import sosteam.deamhome.domain.naver.dto.response.NaverUserInfoResponse
import sosteam.deamhome.domain.naver.exception.NaverTokenNotFoundException
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.security.provider.RandomKeyProvider

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
	private val accountRepository: AccountRepository,
	private val accountStatusValidService: AccountStatusValidService,
	private val randomKeyProvider: RandomKeyProvider
) {
	suspend fun getNaverLoginPage(): String {
		val state = randomKeyProvider.randomAlphabetNumber(32)
		val naverOAuth = "https://nid.naver.com/oauth2.0/authorize"
		val url = UriComponentsBuilder.fromUriString(naverOAuth)
			.queryParam("client_id", clientId).queryParam("response_type", "code")
			.queryParam("redirect_uri", naverRedirectUri).queryParam("state", state).build()
		
		return url.toUriString()
	}
	
	suspend fun naverSign(code: String, state: String): Account {
		val token = getNaverToken(code, state)
		token.accessToken ?: NaverTokenNotFoundException()
		val naverInfo = getNaverUserInfo(token.accessToken!!)
		val SnsId = naverInfo.email.toString().substringBefore("@")
		val user = accountRepository.findAccountBySnsIdAndSns(SnsId, SNS.NAVER)
			?: throw SnsIdNotFoundException()
		accountStatusValidService.getLiveAccountIdByStatus(user.userId, user.sns, user.snsId, user.email)
		return user
	}
	
	suspend fun getNaverToken(code: String, state: String): NaverTokenReturnResponse {
		val naverOAuth = "https://nid.naver.com"
		val naverPath = "/oauth2.0/token"
		val webclient = WebClient.builder().baseUrl(naverOAuth)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build()
		
		val response = webclient.post().uri { uriBuilder ->
			uriBuilder.path(naverPath).queryParam("client_id", clientId)
				.queryParam("client_secret", clientSecret).queryParam("grant_type", "authorization_code")
				.queryParam("state", state).queryParam("code", code).build()
		}.retrieve().onStatus({ status -> status.value() != 200 }) {
			throw NaverTokenNotFoundException()
		}.bodyToMono(NaverTokenReturnResponse::class.java).awaitSingle()
		
		if (response == null || response.accessToken.isNullOrEmpty()) {
			throw NaverTokenNotFoundException()
		}
		
		return response
	}
	
	suspend fun getNaverUserInfo(token: String): NaverUserInfo {
		val naverOAuth = "https://openapi.naver.com"
		val naverPath = "/v1/nid/me"
		val webclient = WebClient.builder().baseUrl(naverOAuth)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build()
		
		val response = webclient.get().uri(naverPath).header("Authorization", "Bearer $token").retrieve()
			.bodyToMono(NaverUserInfoResponse::class.java).awaitSingle()
		
		response.info ?: throw NaverTokenNotFoundException()
		
		return response.info
	}
	
	suspend fun unlinkNaver(token: String): NaverUnlinkResponse {
		val naverOAuth = "https://nid.naver.com"
		val naverPath = "/oauth2.0/token"
		val webclient = WebClient.builder().baseUrl(naverOAuth)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build()
		
		val response = webclient.post().uri { uriBuilder ->
			uriBuilder.path(naverPath).queryParam("client_id", clientId)
				.queryParam("client_secret", clientSecret).queryParam("grant_type", "delete")
				.queryParam("access_token", token).queryParam("service_provider", "NAVER").build()
		}.retrieve().onStatus({ status -> status.value() != 200 }) {
			throw NaverTokenNotFoundException()
		}.bodyToMono(NaverUnlinkResponse::class.java).awaitSingle()
		
		return response
	}
}