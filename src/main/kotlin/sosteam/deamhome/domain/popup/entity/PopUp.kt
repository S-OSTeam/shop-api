package sosteam.deamhome.domain.popup.entity

import jakarta.validation.constraints.NotNull
import lombok.Builder
import lombok.Setter
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.global.entity.BaseEntity
import sosteam.deamhome.global.image.entity.Image
import java.time.LocalDateTime

@Document
@Builder
class PopUp(
	var title: String,
	var createdIp: String,
	
	//팝업 활성화 여부
	var isOnBoard: @NotNull Boolean = false,
	
	var endAt: LocalDateTime,
	
	image: Image,
	account: Account
) : BaseEntity() {
	
	@DocumentReference
	@Setter
	var image: Image = image
	
	@DocumentReference
	@Setter
	val account: Account = account
}