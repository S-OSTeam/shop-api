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
			getAndAddCookie("Authorization", response, "/", Token.ACCESS.time)
			getAndAddCookie("Authorization-Refresh", response, "/", Token.REFRESH.time)
			getAndAddCookie("Authorization-SNS", response, "/signup", 3600)
			getAndAddCookie("Authorization-SNS", response, "/login", 3600)
		}
	}
	
	fun getAndAddCookie(name: String, response: WebGraphQlResponse, path: String, expire: Long) {
		val cookie: String? = response.executionInput.graphQLContext.get(name)
		
		if (!cookie.isNullOrEmpty()) {
			val accessCookie = ResponseCookie.from(name, cookie)
				.maxAge(Duration.ofSeconds(Token.ACCESS.time))
				.sameSite("Strict")
				.httpOnly(true)
				.secure(true)
				.path(path)
				.maxAge(expire)
				.build()
			response.responseHeaders.add(HttpHeaders.SET_COOKIE, accessCookie.toString())
		}
	}
}