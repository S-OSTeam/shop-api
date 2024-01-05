package sosteam.deamhome.domain.auth.service

import org.springframework.http.ResponseCookie
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Service
import sosteam.deamhome.global.attribute.Token
import sosteam.deamhome.global.security.response.TokenResponse
import java.time.Duration

@Service
class AccountAuthCookieService() {
    suspend fun createCookieResponse(
        tokenResponse: TokenResponse,
        response: ServerHttpResponse
    ) {
        val accessCookie = ResponseCookie.from("accessToken", tokenResponse.accessToken)
            .maxAge(Duration.ofSeconds(Token.ACCESS.time))
            .domain("http://localhost:8081/api/graphql")
            .build()
        val refreshCookie = ResponseCookie.from("refreshToken", tokenResponse.refreshToken)
            .maxAge(Duration.ofSeconds(Token.ACCESS.time))
            .domain("http://localhost:8081/api/graphql")
            .build()
        val userId = ResponseCookie.from("userId", tokenResponse.userId)
            .domain("http://localhost:8081/api/graphql")
            .build()
        val issuedAt = ResponseCookie.from("issuedAt", tokenResponse.issuedAt.toString())
            .domain("http://localhost:8081/api/graphql")
            .build()
        response.addCookie(accessCookie)
        response.addCookie(refreshCookie)
        response.addCookie(userId)
        response.addCookie(issuedAt)
    }
}