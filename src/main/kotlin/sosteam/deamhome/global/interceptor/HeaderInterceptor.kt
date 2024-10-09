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
			getAndAddCookie("accessToken", response)
			getAndAddCookie("refreshToken", response)
			getAndAddCookie("snsToken", response)
		}
	}
	
	fun getAndAddCookie(name: String, response: WebGraphQlResponse) {
		val cookie: String? = response.executionInput.graphQLContext.get(name)
		
		if (!cookie.isNullOrEmpty()) {
			val accessCookie = ResponseCookie.from(name, cookie)
				.maxAge(Duration.ofSeconds(Token.ACCESS.time))
				.sameSite("Strict")
				.httpOnly(true)
				.secure(true)
				.build()
			response.responseHeaders.add(HttpHeaders.SET_COOKIE, accessCookie.toString())
		}
	}
}