package sosteam.deamhome.domain.account.handler.response

import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.global.attribute.SNS
import java.time.OffsetDateTime
import java.time.ZoneId

data class AccountResponse(
	//일단 response랑 똑같음.. 추가하려면 추가하기
	val userId: String,
	val sex: Boolean,
	val birthday: OffsetDateTime,
	val zipcode: String,
	val address1: String,
	val address2: String?,
	val address3: String?,
	val address4: String?,
	val email: String,
	val receiveMail: Boolean,
	val snsId: String?,
	val sns: SNS = SNS.NORMAL,
	val phone: String?,
	val userName: String,
	val point: Int,
) {
	companion object {
		fun fromAccount(account: Account): AccountResponse {
			return AccountResponse(
				userId = account.userId,
				sex = account.sex,
				birthday = account.birthday.atZone(ZoneId.systemDefault()).toOffsetDateTime(),
				zipcode = account.zipcode,
				address1 = account.address1,
				address2 = account.address2,
				address3 = account.address3,
				address4 = account.address4,
				email = account.email,
				receiveMail = account.receiveMail,
				snsId = account.snsId,
				sns = account.sns,
				phone = account.phone,
				userName = account.userName,
				point = account.point
			)
		}
	}
}





