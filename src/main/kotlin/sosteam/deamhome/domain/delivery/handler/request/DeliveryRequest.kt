package sosteam.deamhome.domain.delivery.handler.request

data class DeliveryRequest(
	val carrierId: String,
	val trackingNumber: String,
	val last: Int = 10
)