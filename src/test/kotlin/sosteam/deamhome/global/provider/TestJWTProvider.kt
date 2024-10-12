package sosteam.deamhome.global.provider

import org.springframework.security.core.authority.SimpleGrantedAuthority
import sosteam.deamhome.global.attribute.Token
import sosteam.deamhome.global.security.provider.JWTProvider
import sosteam.deamhome.global.security.response.TokenResponse
import java.util.*

class TestJWTProvider : JWTProvider("test-token") {
	override fun generate(
		userId: String,
		roles: List<SimpleGrantedAuthority>,
		ip: String,
		issuedAt: Date
	): TokenResponse {
		val accessToken = createToken(userId, roles, ip, Token.ACCESS, issuedAt)
		val refreshToken = createToken(userId, roles, ip, Token.REFRESH, issuedAt)
		return TokenResponse(accessToken, refreshToken, userId, issuedAt)
	}
	
	override fun createToken(
		userId: String,
		roles: List<SimpleGrantedAuthority>,
		ip: String,
		tokenType: Token,
		issuedAt: Date
	): String {
		return if (tokenType == Token.ACCESS) "accessToken" else "refreshToken"
	}
	
	override fun getLeftTime(token: String): Long {
		return 1000 * 1000L
	}
	
	override fun getUserId(token: String): String {
		return "testAccount"
	}
	
	override fun isValid(token: String, ip: String, tokenType: Token): Boolean {
		return (token == "accessToken" || token == "refreshToken")
	}
}