package sosteam.deamhome.domain.auth.handler.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class AccountChangePwdRequest (
    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @get:Pattern(
        regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
        message = "올바른 이메일 형식이 아닙니다."
    )
    val email: String,

    @Pattern(
        regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,20}$",
        message = "올바른 비밀번호 형식이 아닙니다."
    )
    val pwd: String,

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @Pattern(
        regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,20}$",
        message = "올바른 비밀번호 형식이 아닙니다."
    )
    val confirmPwd: String
)