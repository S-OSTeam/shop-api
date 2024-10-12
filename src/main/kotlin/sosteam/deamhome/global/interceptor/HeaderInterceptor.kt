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
			getAndAddCookie("Authorization", response, request, "/", Token.ACCESS.time)
			getAndAddCookie("Authorization-Refresh", response, request, "/", Token.REFRESH.time)
			getAndAddCookie("Authorization-SNS", response, request, "/signup", 3600)
			getAndAddCookie("Authorization-SNS", response, request, "/login", 3600)
		}
	}
	
	fun getAndAddCookie(
		name: String,
		response: WebGraphQlResponse,
		request: WebGraphQlRequest,
		path: String,
		expire: Long
	) {
		val tokenContext: String? = response.executionInput.graphQLContext.get(name)
		val tokenHeader: String? = request.headers.getFirst(name)
		
		if (!tokenContext.isNullOrEmpty() || !tokenHeader.isNullOrEmpty()) {
			val cookie = (tokenContext ?: tokenHeader)?.let {
				ResponseCookie.from(name, it)
					.maxAge(Duration.ofSeconds(Token.ACCESS.time))
					.sameSite("Strict")
					.httpOnly(true)
					.secure(true)
					.path(path)
					.maxAge(expire)
					.build()
			}
			response.responseHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString())
		}
	}
}