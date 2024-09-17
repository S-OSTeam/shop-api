package sosteam.deamhome.domain.auth.handler.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status
import sosteam.deamhome.global.entity.DTO

data class AccountLoginRequest(
	val userId: String = "",
	val pwd: String?,
	val snsCode: String?,
	val sns: SNS,
	val email: String = "",
) : DTO {
	override fun asDomain(): AccountStatus {
		return AccountStatus(
			// id 는 save 하고 postgreSQL bigSerial 으로 자동 생성
			null, userId, null, sns, email, Status.LIVE
		)
	}
}

