package sosteam.deamhome.domain.item.handler.request

import sosteam.deamhome.domain.item.entity.ItemStatus
import java.time.OffsetDateTime

data class ItemUpdateRequest(
	var publicId: String,
	var categoryPublicId: String?,
	var title: String?,
	var content: String?,
	var summary: String?,
	var price: Int?,
	var status: ItemStatus?,
	var storeId: String?,
	var freeDelivery: Boolean?,
	var option: List<String>?,
	var productNumber: String?,
	var deadline: OffsetDateTime?,
	var originalWork: String?,
	var material: String?,
	var size: String?,
	var weight: String?,
	var shippingCost: Int?,
	var imageUrls: List<String>?
)