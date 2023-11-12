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
	
	@get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
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
	val confirmPwd: String,
	
	val sex: Boolean = false,
	
	val birtyday: LocalDateTime = LocalDateTime.now(),
	
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
	
	val snsId: String?,
	
	@get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
	@get:Pattern(regexp = "^(NORMAL)|(KAKAO)|(GOOGLE)|(NAVER)", message = "sns 종류에 해당하지 않는 값이 입력 되었습니다.")
	val sns: SNS = SNS.NORMAL,
	
	
	val phone: String,
	
	@get:NotBlank(message = "<NULL> <EMPTY> <BLANK>")
	@get:Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 영어, 한글만 허용됩니다.")
	val userName: String,
	val point: Int = 0,
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
			email = this.email,
			status = Status.LIVE,
		)
	}
}

