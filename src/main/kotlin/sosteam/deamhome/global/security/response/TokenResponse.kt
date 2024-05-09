package sosteam.deamhome.global.security.response

import java.time.OffsetDateTime

data class TokenResponse(
	val accessToken: String,
	val refreshToken: String,
	val userId: String,
	val issuedAt: OffsetDateTime
)