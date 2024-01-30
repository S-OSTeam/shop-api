package sosteam.deamhome.domain.mileage.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.LogEntity

@Table("mileage")
class Mileage(
	@Id
	var id: Long?,
	var publicId: String,
	var userId: String,
	var content: String,
	var mileageValue: Int,
	var mileageType: MileageType,
	var orderId: String,
	var action: String
) : LogEntity()