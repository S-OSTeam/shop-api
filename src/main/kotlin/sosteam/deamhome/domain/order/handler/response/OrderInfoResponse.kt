package sosteam.deamhome.domain.order.handler.response

import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.global.attribute.OrderStatus
import java.time.OffsetDateTime

data class OrderInfoResponse(
    val publicId: String,
    val orderStatus: OrderStatus,
    val isPaid: Boolean,
    val isRefund: Boolean,
    val address: String,
    val mainDoorPassword: String,
    val deliveryRequest: String?,
    val userName: String,
    val userNickname: String,
    val addressee: String,
    val email: String,
    val phone: String,
    val memo: String?,
    val userId: String,
    val totalPrice: Int,
    val content: String?,
    val payType: String,
    val appNo: String,
    val bankId: String,
    val orderDateTime: OffsetDateTime,
    val paymentDateTime: OffsetDateTime?,
    val releaseDateTime: OffsetDateTime?,
    val refundDateTime: OffsetDateTime?,
    val deliveryDateTime: OffsetDateTime?,
    val approveDatetime: OffsetDateTime?,
    val couponIds: List<String?>?,
    val pointPrice: Int,
    val shippingCompany: String?,
    val downloadDays: OffsetDateTime,
    val reasonRefund: String?,
    val createdAt: OffsetDateTime,
){
    companion object{
        fun fromOrder(order: Order): OrderInfoResponse {
            return OrderInfoResponse(
                publicId = order.publicId,
                orderStatus = order.orderStatus,
                isPaid = order. isPaid,
                isRefund = order.isRefund,
                address = order.address,
                mainDoorPassword = order.mainDoorPassword,
                deliveryRequest = order.deliveryRequest,
                userName = order.userName,
                userNickname = order.userNickname,
                addressee = order.addressee,
                email = order.email,
                phone = order.phone,
                memo = order.memo,
                userId = order.userId,
                totalPrice = order.totalPrice,
                content = order.content,
                payType = order.payType,
                appNo = order.appNo,
                bankId = order.bankId,
                orderDateTime = order.orderDateTime,
                paymentDateTime = order.paymentDateTime,
                releaseDateTime = order.releaseDateTime,
                refundDateTime= order.refundDateTime,
                deliveryDateTime = order.deliveryDateTime,
                approveDatetime = order.approveDatetime,
                couponIds = order.couponIds,
                pointPrice = order.pointPrice,
                shippingCompany = order.shippingCompany,
                downloadDays = order.downloadDays,
                reasonRefund = order.reasonRefund,
                createdAt = order.getCreatedAt()
            )
        }
    }
}