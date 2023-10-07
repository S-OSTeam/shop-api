package sosteam.deamhome.global.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

abstract class BaseEntity {
	@Id
	private lateinit var id: String
	
	@CreatedDate
	private var createdAt = LocalDateTime.now()
	
	@LastModifiedDate
	private var updatedAt = LocalDateTime.now()
}