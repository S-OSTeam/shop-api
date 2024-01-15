package sosteam.deamhome.domain.account.entity

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status
import sosteam.deamhome.global.entity.BaseEntity
import java.time.LocalDateTime

@Document
data class AccountStatus(
	@Indexed(unique = true)
	val userId: String,

	@Indexed(unique = true)
	val snsId: String? = null,

	val sns: SNS,

	val email: String,

	var updateTime: LocalDateTime,

	var status: Status = Status.LIVE,

	) : BaseEntity() {
	lateinit var accountId: String
}