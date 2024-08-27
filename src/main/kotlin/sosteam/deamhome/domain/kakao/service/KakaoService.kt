package sosteam.deamhome.domain.kakao.service

import kotlinx.coroutines.reactive.awaitSingle
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import sosteam.deamhome.domain.kakao.dto.response.KakaoTokenReturnResponse
import sosteam.deamhome.domain.kakao.dto.response.KakaoUnlinkResponse
import sosteam.deamhome.domain.kakao.dto.response.KakaoUserInfo
import sosteam.deamhome.domain.kakao.exception.KakaoTokenNotFoundException
import sosteam.deamhome.domain.kakao.exception.KakaoUserNotFoundException
import sosteam.deamhome.global.provider.log

@Service
@Slf4j
@RequiredArgsConstructor
class KakaoService(
	@Value("\${spring.security.oauth2.client.kakao.client_id}")
	private val kakaoRestApiToken: String,
	@Value("\${spring.security.oauth2.client.kakao.redirect_uri}")
	private val kakaoRedirectUri: String,
	@Value("\${spring.security.oauth2.client.kakao.state}")
	private val kakaoState: String
) {
	suspend fun getKakaoLoginPage(): String {
		val reqUrl = "https://kauth.kakao.com/oauth/authorize"
		val uriBuilder = UriComponentsBuilder.fromHttpUrl(reqUrl)
			.queryParam("response_type", "code")
			.queryParam("client_id", kakaoRestApiToken)
			.queryParam("redirect_uri", kakaoRedirectUri)
			.queryParam("state", kakaoState)
		
		return uriBuilder.toUriString()
	}
	
	suspend fun getKakaoToken(code: String): String {
		log().info(getKakaoLoginPage())
		val reqUrl = "https://kauth.kakao.com/oauth/token"
		
		val formData = LinkedMultiValueMap<String, String>()
		formData.add("code", code)
		formData.add("grant_type", "authorization_code")
		formData.add("client_id", kakaoRestApiToken)
		formData.add("state", kakaoState)
		
		val response = WebClient.builder()
			.baseUrl(reqUrl)
			.build()
			.post()
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(BodyInserters.fromFormData(formData))
			.retrieve()// 응답을 받을 것
			.bodyToMono(KakaoTokenReturnResponse::class.java)// 본문 응답 타입 지정
			.awaitSingle() // 비동기 응답 대기
		
		if (response == null || response.accessToken.isNullOrEmpty())
			throw KakaoTokenNotFoundException()
		
		return response.accessToken
	}
	
	suspend fun getKakaoUserId(kakaoCode: String): String {
		val reqUrl = "https://kapi.kakao.com/v2/user/me"
		
		val token = getKakaoToken(kakaoCode)
		
		val response = WebClient.builder()
			.baseUrl(reqUrl)
			.build()
			.post()
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.header("Authorization", "Bearer $token")
			.retrieve()
			.bodyToMono(KakaoUserInfo::class.java)
			.awaitSingle()
			.id
		
		if (response == null)
			throw KakaoUserNotFoundException()
		
		return response.toString()
	}
	
	suspend fun unlinkKakao(kakaoCode: String): KakaoUnlinkResponse {
		val reqUrl = "https://kapi.kakao.com/v1/user/unlink"
		
		val token = getKakaoToken(kakaoCode)
		
		val response = WebClient.builder()
			.baseUrl(reqUrl)
			.build()
			.post()
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.header("Authorization", "Bearer " + token)
			.retrieve()
			.bodyToMono(KakaoUnlinkResponse::class.java)
			.awaitSingle()
		
		return response
	}
}