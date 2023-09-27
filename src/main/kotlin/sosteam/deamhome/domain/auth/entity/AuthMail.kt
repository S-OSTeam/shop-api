package sosteam.deamhome.domain.auth.entity

import jakarta.validation.constraints.NotNull
import lombok.Builder
import lombok.Setter
import org.springframework.data.mongodb.core.mapping.Document
import sosteam.deamhome.global.entity.LogEntity
import java.time.LocalDateTime

@Document
@Builder
class AuthMail(
	val email: String,
	val authCode: String,
	@Setter
	var endAt: LocalDateTime = LocalDateTime.now().plusMinutes(10)
) : LogEntity() {
	
	val isSuccess: @NotNull Boolean = false
}