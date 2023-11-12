package sosteam.deamhome.domain.auth.handler.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status
import sosteam.deamhome.global.entity.DTO

data class AccountLoginRequest(
	val userId: String = "",
	
	@get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
	@Pattern(
		regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,20}$",
		message = "올바른 비밀번호 형식이 아닙니다."
	)
	val pwd: String,
	
	@get:Pattern(
		regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
		message = "올바른 이메일 형식이 아닙니다."
	)
	val email: String = "",
	
	val snsId: String?,
	
	@get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
	@get:Pattern(regexp = "^(NORMAL)|(KAKAO)|(GOOGLE)|(NAVER)", message = "sns 종류에 해당하지 않는 값이 입력 되었습니다.")
	val sns: SNS = SNS.NORMAL,
) : DTO {
	override fun asDomain(): AccountStatus {
		return AccountStatus(
			userId, snsId, sns, email, Status.LIVE
		)
	}
}

