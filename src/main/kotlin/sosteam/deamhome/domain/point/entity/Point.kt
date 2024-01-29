package sosteam.deamhome.domain.point.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.LogEntity

@Table("point")
class Point(
	@Id
	var id: Long?,
	var publicId: String,
	var userId: String,
	var content: String,
	var point: Int,
	var pointType: PointType,
	var orderId: String,
	var action: String
) : LogEntity()