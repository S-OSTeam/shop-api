package sosteam.deamhome.domain.auth.handler.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status
import sosteam.deamhome.global.entity.DTO
import java.time.LocalDateTime

data class AccountCreateRequest(
	@get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
	@get:Pattern(regexp = "^[a-z]+[a-z0-9]{5,20}$", message = "올바른 ID 형식이 아닙니다.")
	val userId: String,

	val pwd: String?,

	val confirmPwd: String?,
	
	val sex: Boolean = false,
	
	val birthday: LocalDateTime = LocalDateTime.now(),
	
	val zipcode: String,
	val address1: String,
	val address2: String?,
	val address3: String?,
	val address4: String?,
	
	@get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
	@get:Pattern(
		regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
		message = "올바른 이메일 형식이 아닙니다."
	)
	val email: String,
	val receiveMail: Boolean = false,
	val createdIp: String = "127.0.0.1",
	
	val sns: SNS = SNS.NORMAL,
	
	val phone: String?,
	
	@get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
	@get:Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 영어, 한글만 허용됩니다.")
	val userName: String,
	val point: Int = 0,
) : DTO {
	override fun asDomain(): Account {
		return Account(
			// id 는 save 하고 postgreSQL bigSerial 으로 자동 생성
			id = null,
			userId = this.userId,
			pwd = pwd,
			sex = this.sex,
			birthday = this.birthday,
			zipcode = this.zipcode,
			address1 = this.address1,
			address2 = this.address2,
			address3 = this.address3,
			address4 = this.address4,
			email = this.email,
			receiveMail = this.receiveMail,
			createdIp = this.createdIp,
			adminTxt = "",
			snsId = null,
			sns = this.sns,
			phone = this.phone,
			userName = this.userName,
			point = this.point,
			role = Role.ROLE_USER,
			loginAt = LocalDateTime.now()
		)
	}
	
	fun asStatus(): AccountStatus {
		return AccountStatus(
			// id 는 save 하고 postgreSQL bigSerial 으로 자동 생성
			id = null,
			userId = this.userId,
			snsId = null,
			sns = this.sns,
			email = this.email,
			status = Status.LIVE,
		)
	}
}

