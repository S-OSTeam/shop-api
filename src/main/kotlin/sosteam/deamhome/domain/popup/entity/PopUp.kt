package sosteam.deamhome.domain.popup.entity

import jakarta.validation.constraints.NotNull
import lombok.Setter
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.global.entity.BaseEntity
import sosteam.deamhome.global.image.entity.Image
import java.time.LocalDateTime

@Table("pop_up")
class PopUp(
	@Id
	var id: Long?,
	var title: String,
	var createdIp: String,
	
	//팝업 활성화 여부
	var isOnBoard: @NotNull Boolean = false,
	
	var endAt: LocalDateTime,
	
	image: Image,
	account: Account
) : BaseEntity() {
	
//	@DocumentReference
//	@Setter
//	var image: Image = image
//
//	@DocumentReference
//	@Setter
//	val account: Account = account
}