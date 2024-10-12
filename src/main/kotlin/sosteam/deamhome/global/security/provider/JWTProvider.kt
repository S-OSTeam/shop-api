package sosteam.deamhome.global.security.provider

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import sosteam.deamhome.global.attribute.Token
import sosteam.deamhome.global.security.response.TokenResponse
import java.time.ZoneOffset
import java.util.*

@Component
class JWTProvider(
	@Value("\${jwt.secret.refresh.key}")
	private val refreshKey: String,
	@Value("\${jwt.secret.access.key}")
	
	private val accessKey: String
) {
	private final val encodedAccessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey))
	private final val encodedRefreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey))
	
	fun generate(userId: String, roles: List<SimpleGrantedAuthority>, ip: String, issuedAt: Date): TokenResponse {
		val accessToken = createToken(userId, roles, ip, Token.ACCESS, issuedAt)
		val refreshToken = createToken(userId, roles, ip, Token.REFRESH, issuedAt)
		
		return TokenResponse(accessToken, refreshToken, userId, issuedAt.toInstant().atOffset(ZoneOffset.UTC))
	}
	
	fun createToken(
		userId: String,
		roles: List<SimpleGrantedAuthority>,
		ip: String,
		tokenType: Token,
		issuedAt: Date
	): String =
		"DBearer+" + Jwts.builder()
			.setSubject(userId)
			.setExpiration(Date(issuedAt.time + tokenType.time * 1000))
			.setIssuedAt(issuedAt)
			.addClaims(createClaims(roles, ip, tokenType.type))
			.signWith(
				if (tokenType == Token.REFRESH) {
					encodedRefreshKey
				} else {
					encodedAccessKey
				}, SignatureAlgorithm.HS384
			)
			.compact()
	
	fun extractToken(token: String, tokenType: Token): Claims {
		return Jwts.parserBuilder()
			.setSigningKey(
				if (tokenType == Token.REFRESH) {
					encodedRefreshKey
				} else {
					encodedAccessKey
				}
			)
			.build()
			.parseClaimsJws(token)
			.body
	}
	
	fun getLeftTime(token: String, tokenType: Token): Long {
		val claims = extractToken(token, tokenType)
		
		val expiration = claims.expiration
		
		return expiration.time - System.currentTimeMillis()
	}
	
	fun getUserId(token: String, tokenType: Token): String =
		extractToken(token, tokenType)
			.subject
	
	fun getAuthentication(token: String, tokenType: Token): Authentication {
		val userName = getUserId(token, tokenType)
		val role = getSimpleGrantedAuthority(token, tokenType)
		
		return UsernamePasswordAuthenticationToken(
			userName,
			null,
			role,
		)
	}
	
	fun getSimpleGrantedAuthority(token: String, tokenType: Token): List<SimpleGrantedAuthority> {
		return extractToken(token, tokenType)["role"].toString().split(",").filterNot { it.isEmpty() }
			.map(::SimpleGrantedAuthority)
	}
	
	fun createClaims(roles: List<SimpleGrantedAuthority>, ip: String, type: String): Map<String, Any> {
		var claims = HashMap<String, Any>()
		claims["role"] = roles
		claims["ip"] = ip
		claims["type"] = type
		
		return claims
	}
	
	//ip 및 토큰 동시 검증
	//따로 사용하는 경우가 존재 하지 않으므로 하나로 합침.
	fun isValid(token: String, ip: String, tokenType: Token): Boolean {
		try {
			val body = extractToken(token, tokenType)
			val expireTime = body.expiration
			if (body["type"] != tokenType.type || body["ip"] != ip || expireTime.before(Date()))
				return false
			return true
		} catch (e: Exception) {
			return false
		}
	}
}