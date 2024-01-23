package sosteam.deamhome.global.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

abstract class BaseEntity : Domain {
	@CreatedDate
	private var createdAt = LocalDateTime.now()
	
	@LastModifiedDate
	private var updatedAt = LocalDateTime.now()
	
	fun getCreatedAt(): LocalDateTime {
		return createdAt
	}
}