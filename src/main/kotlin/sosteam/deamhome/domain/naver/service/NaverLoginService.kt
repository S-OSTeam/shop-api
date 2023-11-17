package sosteam.deamhome.domain.naver.service

import kotlinx.coroutines.reactor.awaitSingle
import lombok.RequiredArgsConstructor
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.service.AccountValidService
import sosteam.deamhome.domain.auth.exception.TokenNotValidException
import sosteam.deamhome.domain.auth.service.AccountAuthCreateService
import sosteam.deamhome.domain.naver.exception.NaverTokenNotFoundException
import sosteam.deamhome.domain.naver.handler.response.UrlResponse
import sosteam.deamhome.global.provider.RequestProvider.Companion.getMac
import sosteam.deamhome.global.security.provider.RandomKeyProvider
import sosteam.deamhome.global.security.response.TokenResponse
import java.util.*

@Service
@RequiredArgsConstructor
class NaverLoginService(
    private val accountValidService: AccountValidService,
    private val accountAuthCreateService: AccountAuthCreateService,
    @Value("\${spring.security.oauth2.client.registration.naver.client-id}")
    private val clientId: String,
    @Value("\${spring.security.oauth2.client.registration.naver.client-secret}")
    private val clientSecret: String,
) {
    fun naverConnect(
        randomKeyProvider: RandomKeyProvider
    ): UrlResponse {
        val state = randomKeyProvider.randomAlphabetNumber(32)
        val url = UriComponentsBuilder
            .fromUriString("https://nid.naver.com/oauth2.0/authorize")
            .queryParam("client_id", clientId)
            .queryParam("response_type", "code")
            .queryParam("redirect_uri", "http://localhost:8080/api/naver/callback")
            .queryParam("state", state)
            .build()

        return UrlResponse(url.toString())
    }

    suspend fun naverLogin(code: String, state: String): TokenResponse {
        val webclient = WebClient.builder()
            .baseUrl("https://nid.naver.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        val response = getNaverToken(webclient, state, code) as Map<String, Any>

        val token = response.get("access_token") as String
        if (token.isNullOrEmpty()) {
            throw TokenNotValidException()
        }
        val userInfo = getUserInfo(token).awaitSingle()
        val snsId = userInfo.get("id").toString()

        // DB에 account 계정이 있는지 확인
        val account = accountValidService.getAccountBySnsId(snsId) as Account
        if (account != null) {
            throw AccountNotFoundException()
        }

        // 토큰 발급
        val mac = getMac()
        val accountLoginDTO = accountValidService.getAccountLoginDTO(account.id, account.pwd)
        return accountAuthCreateService.createTokenResponse(accountLoginDTO, mac)
    }

    private suspend fun getNaverToken(
        webclient: WebClient,
        state: String,
        code: String
    ): MutableMap<String, Any>? {
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
            .bodyToMono(String::class.java)
            .map { responseBody ->
                JSONObject(responseBody)
            }
            .map { jsonObject ->
                jsonObject.toMap()
            }
            .awaitSingle()
        return response
    }

    private suspend fun getUserInfo(accessToken: String): Mono<Map<String, Any>> {
        val webclient = WebClient.builder()
            .baseUrl("https://openapi.naver.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

        return webclient.get()
            .uri("/v1/nid/me")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .bodyToMono(String::class.java)
            .map { responseBody ->
                val jsonObject = JSONObject(responseBody)
                jsonObject.getJSONObject("response").toMap()
            }
    }
}