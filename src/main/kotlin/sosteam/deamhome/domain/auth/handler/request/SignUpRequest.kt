package sosteam.deamhome.domain.auth.handler.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime

data class SignUpRequest(
	@field:NotBlank(message = "id는 공백일 수 없습니다.")
	@field:Pattern(regexp = "^[a-z0-9]{5,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 5~20자리여야 합니다.")
	val userId: String,
	
	@field:NotBlank(message = "비밀번호는 공백일 수 없습니다.")
	@field:Pattern(
		regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
		message = "비밀번호는 8~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요."
	)
	val pwd: String,
	
	val sex: Boolean = false,
	val birthday: LocalDateTime = LocalDateTime.now(),
	
	@field:NotBlank(message = "주소를 입력해주세요.")
	val zipcode: String,
	
	@field:NotBlank(message = "주소를 입력해주세요.")
	val address1: String,
	@field:NotBlank(message = "주소를 입력해주세요.")
	val address2: String? = null,
	@field:NotBlank(message = "주소를 입력해주세요.")
	val address3: String? = null,
	@field:NotBlank(message = "주소를 입력해주세요.")
	val address4: String? = null,
	
	@field:NotBlank(message = "이메일을 입력해주세요.")
	@field:Pattern(
		regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
		message = "옳바른 이메일을 입력해주세요."
	)
	val email: String,
	
	@field:NotBlank(message = "전화번호를 입력해주세요.")
	val phone: String,
	val receiveMail: Boolean? = false,
	
	val snsId: String? = null,
	@field:NotBlank(message = "SNS 종류를 입력해주세요.")
	val sns: String,
	
	@field:NotBlank(message = "사용하실 이름을 입력해주세요.")
	val userName: String,
)
