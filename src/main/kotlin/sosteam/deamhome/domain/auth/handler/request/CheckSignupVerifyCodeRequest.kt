package sosteam.deamhome.domain.auth.handler.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CheckSignupVerifyCodeRequest (
    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Pattern(
        regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
        message = "올바른 이메일 형식이 아닙니다."
    )
    val email: String,
    @get:Pattern(regexp = "\\d{6}", message = "인증 코드는 6자리 문자열 입니다.")
    val verifyCode: String
)