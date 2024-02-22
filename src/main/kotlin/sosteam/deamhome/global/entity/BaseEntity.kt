package sosteam.deamhome.global.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import java.time.OffsetDateTime

abstract class BaseEntity : Domain {
	@CreatedDate
	@Column("created_at")
	private var createdAt = OffsetDateTime.now()

	@LastModifiedDate
	@Column("updated_at")
	private var updatedAt = OffsetDateTime.now()
	
	fun getCreatedAt(): OffsetDateTime {
		return createdAt
	}
}