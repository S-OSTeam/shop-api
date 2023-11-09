package sosteam.deamhome.global.security.response

import java.util.*

data class TokenResponse(
	val accessToken: String,
	val refreshToken: String,
	val issuedAt: Date
)