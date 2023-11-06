package sosteam.deamhome.domain.account.dto.request

import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status
import sosteam.deamhome.global.entity.DTO
import java.time.LocalDateTime

data class AccountRequestDTO(
	val userId: String,
	val pwd: String,
	val sex: Boolean,
	val birtyday: LocalDateTime,
	val zipcode: String,
	val address1: String,
	val address2: String?,
	val address3: String?,
	val address4: String?,
	val email: String,
	val receiveMail: Boolean,
	val createdIp: String,
	val snsId: String,
	val sns: SNS = SNS.NORMAL,
	val phone: String,
	val userName: String,
	val point: Int,
) : DTO {
	override fun asDomain(): Account {
		return Account(
			this.userId,
			this.pwd,
			this.sex,
			this.birtyday,
			this.zipcode,
			this.address1,
			this.address2,
			this.address3,
			this.address4,
			this.email,
			this.receiveMail,
			this.createdIp,
			"",
			this.snsId,
			this.sns,
			this.phone,
			this.userName,
			this.point,
			Role.ROLE_USER,
			LocalDateTime.now()
		)
	}
	
	fun asStatus(): AccountStatus {
		return AccountStatus(
			userId = this.userId,
			snsId = this.snsId,
			sns = this.sns,
			status = Status.LIVE,
		)
		
	}
}

