package sosteam.deamhome.domain.order.handler.request
import sosteam.deamhome.domain.order.entity.Order

// 주소, 현관문 비번, 주문 요청사항, 메모, 수신인
data class OrderUpdateRequest (
    val orderId: String,
    val address: String,
    val mainDoorPassword: String,
    val deliveryRequest: String?,
    val addressee: String,
    val memo: String?,
) {
    fun asOrder(order: Order): Order{

        order.address = address
        order.mainDoorPassword = mainDoorPassword
        order.deliveryRequest = deliveryRequest
        order.addressee = addressee
        order.memo = memo

        return order
    }
}
