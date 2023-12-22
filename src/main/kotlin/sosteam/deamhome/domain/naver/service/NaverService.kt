package sosteam.deamhome.domain.naver.service

import kotlinx.coroutines.reactor.awaitSingle
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import sosteam.deamhome.domain.naver.exception.NaverTokenNotFoundException
import sosteam.deamhome.domain.naver.handler.response.NaverTokenReturnResponse
import sosteam.deamhome.domain.naver.handler.response.NaverUnlinkResponse
import sosteam.deamhome.domain.naver.handler.response.NaverUserInfo
import sosteam.deamhome.global.security.provider.RandomKeyProvider
import java.util.*

@Service
@RequiredArgsConstructor
class NaverService(
    private val randomKeyProvider: RandomKeyProvider,
    @Value("\${spring.security.oauth2.client.registration.naver.client-id}")
    private val clientId: String,
    @Value("\${spring.security.oauth2.client.registration.naver.client-secret}")
    private val clientSecret: String,
) {
    suspend fun getNaverLoginPage(): String {
        val state = randomKeyProvider.randomAlphabetNumber(32)
        val url = UriComponentsBuilder
            .fromUriString("https://nid.naver.com/oauth2.0/authorize")
            .queryParam("client_id", clientId)
            .queryParam("response_type", "code")
            .queryParam("redirect_uri", "http://localhost:8080/api/naver/callback")
            .queryParam("state", state)
            .build()

        return url.toUriString()
    }

    suspend fun naverSign(code: String, state: String): NaverTokenReturnResponse {
        val token = getNaverToken(code, state)
        getNaverUserInfo(token.accessToken)
        return token
    }

    suspend fun getNaverToken(code: String, state: String): NaverTokenReturnResponse {
        val webclient = WebClient.builder()
            .baseUrl("https://nid.naver.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        val response = webclient.post()
            .uri { uriBuilder ->
                uriBuilder.path("/oauth2.0/token")
                    .queryParam("client_id", clientId)
                    .queryParam("client_secret", clientSecret)
                    .queryParam("grant_type", "authorization_code")
                    .queryParam("state", state)
                    .queryParam("code", code)
                    .build()
            }
            .retrieve()
            .onStatus({ status -> status.value() != 200 }) {
                throw NaverTokenNotFoundException()
            }
            .bodyToMono(NaverTokenReturnResponse::class.java)
            .awaitSingle()

        if (response == null || response.accessToken.isNullOrEmpty()) {
            throw NaverTokenNotFoundException()
        }

        return response
    }

    suspend fun getNaverUserInfo(token: String): NaverUserInfo {
        val webclient = WebClient.builder()
            .baseUrl("https://openapi.naver.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        val response = webclient.get()
            .uri("/v1/nid/me")
            .header("Authorization", "Bearer $token")
            .retrieve()
            .bodyToMono(NaverUserInfo::class.java)
            .awaitSingle()

        if (response == null) {
            throw NaverTokenNotFoundException()
        }

        return response
    }

    suspend fun unlinkNaver(token: String): NaverUnlinkResponse {
        val webclient = WebClient.builder()
            .baseUrl("https://nid.naver.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        val response = webclient.post()
            .uri { uriBuilder ->
                uriBuilder.path("/oauth2.0/token")
                    .queryParam("client_id", clientId)
                    .queryParam("client_secret", clientSecret)
                    .queryParam("grant_type", "delete")
                    .queryParam("access_token", token)
                    .queryParam("service_provider", "NAVER")
                    .build()
            }
            .retrieve()
            .onStatus({ status -> status.value() != 200 }) {
                throw NaverTokenNotFoundException()
            }
            .bodyToMono(NaverUnlinkResponse::class.java)
            .awaitSingle()

        return response
    }
}