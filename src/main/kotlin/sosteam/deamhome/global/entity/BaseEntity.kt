package sosteam.deamhome.global.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

abstract class BaseEntity {
	@Id
	private val id: String? = null
	
	@CreatedDate
	private val createdAt = LocalDateTime.now()
	
	@LastModifiedDate
	private val updatedAt = LocalDateTime.now()
}