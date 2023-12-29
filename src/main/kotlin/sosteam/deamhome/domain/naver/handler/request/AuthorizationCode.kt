package sosteam.deamhome.domain.naver.handler.request

import jakarta.validation.constraints.NotBlank

data class AuthorizationCode(
	@get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
	val code: String,
	
	@get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
	val state: String
)
