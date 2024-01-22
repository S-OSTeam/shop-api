package sosteam.deamhome.domain.auth.entity

import jakarta.validation.constraints.NotNull
import lombok.Setter
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.LogEntity
import java.time.LocalDateTime

@Table("auth_mail")
class AuthMail(
	@Id
	var id: Long?,
	val email: String,
	val authCode: String,
	@Setter
	var endAt: LocalDateTime = LocalDateTime.now().plusMinutes(10)
) : LogEntity() {
	
	val isSuccess: @NotNull Boolean = false
}