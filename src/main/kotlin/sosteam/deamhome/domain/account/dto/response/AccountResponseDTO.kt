package sosteam.deamhome.domain.account.dto.response

import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.global.attribute.SNS
import java.time.LocalDateTime

data class AccountResponseDTO ( //일단 response랑 똑같음.. 추가하려면 추가하기
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
    val snsId: String?,
    val sns: SNS = SNS.NORMAL,
    val phone: String,
    val userName: String,
    val point: Int,
){
    companion object {
        fun fromAccount(account: Account): AccountResponseDTO {
            return AccountResponseDTO(
                userId = account.userId,
                pwd = account.pwd,
                sex = account.sex,
                birtyday = account.birtyday,
                zipcode = account.zipcode,
                address1 = account.address1,
                address2 = account.address2,
                address3 = account.address3,
                address4 = account.address4,
                email = account.email,
                receiveMail = account.receiveMail,
                createdIp = account.createdIp,
                snsId = account.snsId,
                sns = account.sns,
                phone = account.phone,
                userName = account.userName,
                point = account.point
            )
        }
    }
}





