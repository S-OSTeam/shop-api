package sosteam.deamhome.domain.account.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status
import sosteam.deamhome.global.entity.BaseEntity
import java.time.LocalDateTime

@Table("account_status")
data class AccountStatus(
	@Id
	var id: Long?,
	// unique column
	val userId: String,

	// unique column
	val snsId: String? = null,

	val sns: SNS,

	val email: String,

	var deletedAT: LocalDateTime? = LocalDateTime.now(),

	var status: Status = Status.LIVE,

	) : BaseEntity() {
	var accountId: Long? = null
}