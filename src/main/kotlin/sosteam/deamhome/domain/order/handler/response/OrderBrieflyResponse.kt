package sosteam.deamhome.domain.order.handler.response

import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.global.attribute.OrderStatus
import java.time.OffsetDateTime

// order 리스트 아이템 간략 정보 표기
data class OrderBrieflyResponse (
    val publicId: String,
    val payType: String,
    val totalPrice: Int,
    val orderStatus: OrderStatus,
    val orderDateTime: OffsetDateTime,
    // Todo : 구매한 아이템 하나 추가할지..
){
    companion object{
        fun fromOrder(order: Order): OrderBrieflyResponse {
            return OrderBrieflyResponse(
                publicId = order.publicId,
                payType = order.payType,
                totalPrice = order.totalPrice,
                orderStatus = order.orderStatus,
                orderDateTime = order.orderDateTime,
            )
        }
    }
}