package sosteam.deamhome.domain.auth.handler.request

import jakarta.validation.constraints.Pattern

data class CheckChangePwdVerifyCodeRequest (
    val userId: String,
    @get:Pattern(regexp = "\\d{6}", message = "인증 코드는 6자리 문자열 입니다.")
    val verifyCode: String
)