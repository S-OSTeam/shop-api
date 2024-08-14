package sosteam.deamhome.global.interceptor

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
			val accessToken: String? = response.executionInput.graphQLContext.get("accessToken")
			val refreshToken: String? = response.executionInput.graphQLContext.get("refreshToken")
			if (!accessToken.isNullOrEmpty()) {
				val accessCookie = ResponseCookie.from("accessToken", accessToken)
					.maxAge(Duration.ofSeconds(Token.ACCESS.time))
					.sameSite("Strict")
					.httpOnly(true)
					.secure(true)
					.build()
				response.responseHeaders.add(HttpHeaders.SET_COOKIE, accessCookie.toString())
			}
			if (!refreshToken.isNullOrEmpty()) {
				val refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
					.maxAge(Duration.ofSeconds(Token.ACCESS.time))
					.sameSite("Strict")
					.httpOnly(true)
					.secure(true)
					.build()
				response.responseHeaders.add(HttpHeaders.SET_COOKIE, refreshCookie.toString())
			}
		}
	}
}