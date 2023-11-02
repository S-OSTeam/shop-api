package sosteam.deamhome.domain.kakao.service

import kotlinx.coroutines.reactive.awaitSingle
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import sosteam.deamhome.domain.kakao.dto.response.KakaoTokenReturnResponse

@Service
@Transactional
@Slf4j
class KakaoService(
    @Value("\${spring.security.oauth2.client.kakao.client_id}")
    private val kakaoRestApiToken: String,
    @Value("\${spring.security.oauth2.client.kakao.redirect_code_uri}")
    private val kakaoRedirectCodeUri: String,
    @Value("\${spring.security.oauth2.client.kakao.redirect_uri}")
    private val kakaoRedirectUri: String,
    @Value("\${spring.security.oauth2.client.kakao.client_secret}")
    private val kakaoClientSecret: String
) {
    suspend fun getKakaoLoginPage(): String {
        val reqUrl = "https://kauth.kakao.com/oauth/authorize"
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(reqUrl)
            .queryParam("response_type", "code")
            .queryParam("client_id", kakaoRestApiToken)
            .queryParam("redirect_uri", kakaoRedirectUri)

        return uriBuilder.toUriString()
    }

    suspend fun getKakaoToken(code: String): KakaoTokenReturnResponse {
        val reqUrl = "https://kauth.kakao.com/oauth/token"

        val formData = LinkedMultiValueMap<String, String>()
        formData.add("code", code)
        formData.add("grant_type", "authorization_code")
        formData.add("client_id", kakaoRestApiToken)
        formData.add("redirect_uri", kakaoRedirectUri)
        //formData.add("client_secret", kakaoClientSecret)

        val response = WebClient.builder()
            .baseUrl(reqUrl)
            .build()
            .post()
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(KakaoTokenReturnResponse::class.java)// 본문 응답 타입 지정
            .awaitSingle() // 비동기 응답 대기

        return response
    }
}