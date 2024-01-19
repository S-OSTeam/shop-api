/*package sosteam.deamhome.global.Interceptor

import org.springframework.graphql.server.WebGraphQlInterceptor
import org.springframework.graphql.server.WebGraphQlRequest
import org.springframework.graphql.server.WebGraphQlResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import sosteam.deamhome.global.attribute.Token
import java.time.Duration

@Component
class HeaderInterceptor : WebGraphQlInterceptor {
    override fun intercept(request: WebGraphQlRequest, chain: WebGraphQlInterceptor.Chain): Mono<WebGraphQlResponse> {
        return chain.next(request).doOnNext { response ->
            val accessToken: String = response.executionInput.graphQLContext.get("accessToken")
            val refreshToken: String = response.executionInput.graphQLContext.get("refreshToken")
            val userId: String = response.executionInput.graphQLContext.get("userId")
            val accessCookie = ResponseCookie.from("accessToken", accessToken)
                    .maxAge(Duration.ofSeconds(Token.ACCESS.time))
                    .build()
            val refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .maxAge(Duration.ofSeconds(Token.ACCESS.time))
                    .build()
            val userIdCookie = ResponseCookie.from("userId", userId)
                    .build()
            response.responseHeaders.add(HttpHeaders.SET_COOKIE, accessCookie.toString())
            response.responseHeaders.add(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            response.responseHeaders.add(HttpHeaders.SET_COOKIE, userIdCookie.toString())
        }
    }
}*/