package sosteam.deamhome.domain.account.entity

import jakarta.validation.constraints.Null
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
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

	var status: Status = Status.LIVE,

	) : BaseEntity() {
	var accountId: Long? = null
	@Column("deleted_at")
	var deletedAt: LocalDateTime? = LocalDateTime.now()
}