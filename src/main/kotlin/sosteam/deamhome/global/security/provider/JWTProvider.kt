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
	@Value("\${jwt.secret.key}")
	private val secretKey: String
) {
	private final val encodedSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
	
	fun generate(userId: String, roles: List<SimpleGrantedAuthority>, mac: String, issuedAt: Date): TokenResponse {
		val accessToken = createToken(userId, roles, mac, Token.ACCESS, issuedAt)
		val refreshToken = createToken(userId, roles, mac, Token.REFRESH, issuedAt)
		
		return TokenResponse(accessToken, refreshToken, userId, issuedAt.toInstant().atOffset(ZoneOffset.UTC))
	}
	
	fun createToken(
		userId: String,
		roles: List<SimpleGrantedAuthority>,
		mac: String,
		tokenType: Token,
		issuedAt: Date
	): String =
		"DBearer " + Jwts.builder()
			.setSubject(userId)
			.setExpiration(Date(issuedAt.time + tokenType.time))
			.setIssuedAt(issuedAt)
			.addClaims(createClaims(roles, mac, tokenType.type))
			.signWith(encodedSecretKey, SignatureAlgorithm.HS384)
			.compact()
	
	fun extractToken(token: String): Claims {
		return Jwts.parserBuilder()
			.setSigningKey(encodedSecretKey)
			.build()
			.parseClaimsJws(token)
			.body
	}
	
	fun getLeftTime(token: String): Long {
		val claims = extractToken(token)
		
		val expiration = claims.expiration
		
		return expiration.time - System.currentTimeMillis()
	}
	
	fun getUserId(token: String): String =
		extractToken(token)
			.subject
	
	fun getAuthentication(token: String): Authentication {
		val userName = getUserId(token)
		val role = getSimpleGrantedAuthority(token)
		
		return UsernamePasswordAuthenticationToken(
			userName,
			null,
			role,
		)
	}
	
	fun getSimpleGrantedAuthority(token: String): List<SimpleGrantedAuthority> {
		return extractToken(token)["role"].toString().split(",").filterNot { it.isEmpty() }
			.map(::SimpleGrantedAuthority)
	}
	
	fun createClaims(roles: List<SimpleGrantedAuthority>, mac: String, type: String): Map<String, Any> {
		var claims = HashMap<String, Any>()
		claims["role"] = roles
		claims["mac"] = mac
		claims["type"] = type
		
		return claims
	}
	
	//mac 및 토큰 동시 검증
	//따로 사용하는 경우가 존재 하지 않으므로 하나로 합침.
	fun isValid(token: String, mac: String, tokenType: Token): Boolean {
		try {
			val body = extractToken(token)
			val expireTime = body.expiration
			if (body["kind"] != tokenType.type || body["mac"] != mac || expireTime.before(Date()))
				return false
			return true
		} catch (e: Exception) {
			return false
		}
	}
}