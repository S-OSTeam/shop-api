package sosteam.deamhome.global.entity

import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

abstract class BaseEntity : Domain {
	@Id
	val id: String = UlidCreator.getMonotonicUlid().toString()
	
	@CreatedDate
	private var createdAt = LocalDateTime.now()
	
	@LastModifiedDate
	private var updatedAt = LocalDateTime.now()
}