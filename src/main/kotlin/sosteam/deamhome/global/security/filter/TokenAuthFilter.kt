package sosteam.deamhome.global.security.filter

import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import sosteam.deamhome.global.attribute.Token
import sosteam.deamhome.global.security.provider.JWTProvider
import sosteam.deamhome.global.security.provider.RedisProvider

//@Component
class TokenAuthFilter(val jwtProvider: JWTProvider, val redisProvider: RedisProvider) : WebFilter {
	override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
		val request = exchange.request
		val token = getTokenFromRequest(request)
		val mac = getMacFromRequest(request)
		
		if (!mac.isNullOrEmpty() && !token.isNullOrEmpty() && redisProvider.getData(token)
				.isNullOrEmpty() && jwtProvider.isValid(
				token,
				mac,
				Token.ACCESS
			)
		) {
			val authentication = jwtProvider.getAuthentication(token)
			
			return chain.filter(exchange)
				.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
		}
		return chain.filter(exchange)
	}
	
	fun getMacFromRequest(request: ServerHttpRequest): String? {
		val macAddress = request.headers.getFirst("Authorization-Mac")
		if (!macAddress.isNullOrEmpty() && macAddress.length == 17)
			return macAddress
		return null
	}
	
	fun getTokenFromRequest(request: ServerHttpRequest): String? {
		val bearerToken = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
		if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith("DBearer "))
			return bearerToken.substring(8)
		return null
	}
}