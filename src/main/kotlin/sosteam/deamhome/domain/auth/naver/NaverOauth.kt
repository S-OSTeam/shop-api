package sosteam.deamhome.domain.auth.naver

import kotlinx.coroutines.reactor.awaitSingle
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import sosteam.deamhome.global.provider.RandomKeyProvider

@RestController
@RequestMapping("/naver")
class NaverOauth(
    @Value("\${spring.security.oauth2.client.registration.naver.client-id}")
    private val clientId: String,
    @Value("\${spring.security.oauth2.client.registration.naver.client-secret}")
    private val clientSecret: String,
) {
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/oauth")
    fun naverConnect(
        randomKeyProvider: RandomKeyProvider
    ): String {
        val state = randomKeyProvider.randomAlphabetNumber(32)
        val url = StringBuilder()

        url.append("https://nid.naver.com/oauth2.0/authorize?")
        url.append("client_id=" + clientId)
        url.append("&response_type=code")
        url.append("&redirect_uri=http://localhost:8081/api/naver/callback")
        url.append("&state=" + state)

        return url.toString()
    }

    @GetMapping("/callback")
    suspend fun naverLogin(
        @RequestParam("code") code: String,
        @RequestParam("state") state: String
    ): Map<String, Any> {
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
            .bodyToMono(String::class.java)
            .map { responseBody ->
                JSONObject(responseBody)
            }
            .map { jsonObject ->
                jsonObject.toMap()
            }
            .awaitSingle()


        val token = response.get("access_token") as String
        return getUserInfo(token).awaitSingle()
    }

    suspend fun getUserInfo(accessToken: String): Mono<Map<String, Any>> {
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