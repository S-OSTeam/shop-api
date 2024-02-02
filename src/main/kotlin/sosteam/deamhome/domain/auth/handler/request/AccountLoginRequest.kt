package sosteam.deamhome.domain.auth.handler.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status
import sosteam.deamhome.global.entity.DTO
import java.time.LocalDateTime

data class AccountLoginRequest(
	val userId: String = "",
	
	@get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
	@Pattern(
		regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,20}$",
		message = "올바른 비밀번호 형식이 아닙니다."
	)
	val pwd: String,
	
	val email: String = "",
	
	val snsId: String?,
	
	val sns: SNS = SNS.NORMAL,
) : DTO {
	override fun asDomain(): AccountStatus {
		return AccountStatus(
			// id 는 save 하고 postgreSQL bigSerial 으로 자동 생성
			null, userId, snsId, sns, email, LocalDateTime.now() ,Status.LIVE
		)
	}
}

