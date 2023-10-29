package sosteam.deamhome.domain.account.entity

import lombok.Builder
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.attribute.Status
import sosteam.deamhome.global.entity.BaseEntity

@Document
@Builder
class AccountStatus(
	@Indexed(unique = true)
	val userId: String,
	
	@Indexed(unique = true)
	val snsId: String,
	
	var status: Status = Status.LIVE,
	
	) : BaseEntity()