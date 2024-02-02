package sosteam.deamhome.domain.auth.handler.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class AccountChangePwdRequest (
    val userId: String = "",

    @Pattern(
        regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,20}$",
        message = "올바른 비밀번호 형식이 아닙니다."
    )
    val pwd: String = "",

    @get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
    @Pattern(
        regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,20}$",
        message = "올바른 비밀번호 형식이 아닙니다."
    )
    val confirmPwd: String = ""
)