package sosteam.deamhome.domain.item.handler.request

import sosteam.deamhome.global.attribute.ItemStatus
import sosteam.deamhome.global.image.handler.request.ImageRequest
import java.time.OffsetDateTime

data class ItemUpdateRequest (
    var publicId: String,
    var categoryPublicId: String?,
    var title: String?,
    var content: String?,
    var summary: String?,
    var price: Int?,
    var status: ItemStatus?,
    var sellerId: String?,
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
) {
}