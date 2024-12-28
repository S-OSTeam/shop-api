package sosteam.deamhome.global.security.filter

import com.google.common.net.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import sosteam.deamhome.global.attribute.Token
import sosteam.deamhome.global.security.provider.JWTProvider
import sosteam.deamhome.global.security.provider.RedisProvider
import sosteam.deamhome.global.security.response.TokenResponse
import java.net.HttpCookie
import java.util.*

@Component
class TokenAuthFilter(
	val jwtProvider: JWTProvider,
	val redisProvider: RedisProvider
) : WebFilter {
	override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
		val request = exchange.request
		var accessToken = getAccessTokenFromRequest(request)
		var refreshToken = getRefreshTokenFromRequest(request)
		var generateToken: TokenResponse? = null
		val ip = getIP(request)
		
		
		//ip는 존재해야 하며, accessToken이 없으면 refreshToken이 있어야하고 (reIssue), accessToken이 있으면 redis에 존재하지 않아야 함(로그아웃)
		val isUsableAccessToken = isUsableAccessToken(accessToken, ip, redisProvider, jwtProvider)
		if (isUsableAccessToken || !refreshToken.isNullOrEmpty()) {
			
			//accessToken이 만료되거나 없으면 refreshToken 및 accessToken 재 발급
			if (!isUsableAccessToken && !refreshToken.isNullOrEmpty() && isUsableRefreshToken(
					refreshToken,
					ip,
					redisProvider,
					jwtProvider
				)
			) {
				val issuedAt = Date(System.currentTimeMillis())
				val userId = jwtProvider.getUserId(refreshToken, Token.REFRESH)
				val roles = jwtProvider.getSimpleGrantedAuthority(refreshToken, Token.REFRESH)
				
				generateToken = jwtProvider.generate(userId, roles, ip, issuedAt)
				
				accessToken = generateToken.accessToken
				refreshToken = generateToken.refreshToken
				
				redisProvider.setDataExpire(userId, refreshToken, Token.REFRESH.time)
			}
			
			val authentication =
				accessToken?.let { jwtProvider.getAuthentication(it.replace("DBearer+", ""), Token.ACCESS) }
			
			if (authentication != null)
				chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
			
			if (generateToken != null) {
				val cookie = HttpCookie("Authorization", accessToken.toString())
				exchange.request.mutate().headers {
					it.set(HttpHeaders.COOKIE, cookie.toString())
					it.set("Authorization", accessToken)
					it.set("Authorization-Refresh", refreshToken)
				}.build()
			}
		}
		return chain.filter(exchange.mutate().request(request).build())
	}
	
	fun isUsableAccessToken(
		accessToken: String?,
		ip: String,
		redisProvider: RedisProvider,
		jwtProvider: JWTProvider
	): Boolean {
		if (accessToken.isNullOrEmpty())
			return false
		
		if (!redisProvider.getData(accessToken).isNullOrEmpty())
			return false
		
		if (!jwtProvider.isValid(accessToken, ip, Token.ACCESS))
			return false
		
		return true
	}
	
	fun isUsableRefreshToken(
		refreshToken: String?,
		ip: String,
		redisProvider: RedisProvider,
		jwtProvider: JWTProvider
	): Boolean {
		if (refreshToken.isNullOrEmpty())
			return false
		
		val userId = jwtProvider.getUserId(refreshToken, Token.REFRESH)
		val token = redisProvider.getData(userId)
		if (token.isNullOrEmpty() || token != "DBearer+" + refreshToken)
			return false
		
		if (!jwtProvider.isValid(refreshToken, ip, Token.REFRESH))
			return false
		
		return true
	}
	
	fun getIP(request: ServerHttpRequest): String {
		val headers = request.headers
		val ip = headers.getFirst("X-Real-IP") ?: headers.getFirst("X-Forwarded-For") ?: "127.0.0.1"
		
		return ip
	}
	
	fun getAccessTokenFromRequest(request: ServerHttpRequest): String? {
		val bearerToken = request.cookies.getFirst("Authorization")?.value
		
		if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith("DBearer+"))
			return bearerToken.substring(8)
		return null
	}
	
	fun getRefreshTokenFromRequest(request: ServerHttpRequest): String? {
		val bearerToken = request.cookies.getFirst("Authorization-Refresh")?.value
		if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith("DBearer+"))
			return bearerToken.substring(8)
		return null
	}
}