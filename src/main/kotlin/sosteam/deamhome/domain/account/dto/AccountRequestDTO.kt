package sosteam.deamhome.domain.account.dto

import org.springframework.data.mongodb.core.index.Indexed
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import java.time.LocalDateTime

data class AccountRequestDTO (
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
)

