package sosteam.deamhome.domain.account.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status
import sosteam.deamhome.global.entity.BaseEntity
import java.time.OffsetDateTime

@Table("account_status")
data class AccountStatus(
	@Id
	var id: Long?,
	// unique column
	@Column("user_id")
	val userId: String,
	
	// unique column
	@Column("sns_id")
	var snsId: String? = null,
	
	val sns: SNS,
	
	val email: String,
	
	var status: Status = Status.LIVE,
	
	@Column("account_id")
	var accountId: Long? = null

) : BaseEntity() {
	@Column("deleted_at")
	var deletedAt: OffsetDateTime? = null
}