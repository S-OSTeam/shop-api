package sosteam.deamhome.domain.order.handler.request

import sosteam.deamhome.domain.order.entity.Order
import sosteam.deamhome.global.entity.DTO
import java.time.OffsetDateTime

data class OrderRequest (
    val address: String,
    val mainDoorPassWord: String,
    val deliveryRequest: String?,
    val userName: String,
    var userNickname: String,
    var addressee: String,
    val email: String,
    val phone: String,
    var memo: String?,
    val userId: String,
//    var totalPrice: Int,
    val content: String,
    var payType: String,
    val appNo: String,
    val bankId: String,
//    val orderDateTime: OffsetDateTime,
    val couponIds: List<String?>?,
    val pointPrice: Int,
    var shippingCompany: String,
    val downloadDays: OffsetDateTime,
): DTO {
    override fun asDomain(): Order {
        return Order(
            id = null,
            address = address,
            mainDoorPassWord = mainDoorPassWord,
            deliveryRequest = deliveryRequest,
            userName = userName,
            userNickname = userNickname,
            addressee = addressee,
            email = email,
            phone = phone,
            memo = memo,
            userId = userId,
            totalPrice = 0,
            content = content,
            payType = payType,
            appNo = appNo,
            bankId = bankId,
            orderDateTime = OffsetDateTime.now(),
            paymentDateTime = null,
            releaseDateTime = null,
            refundDateTime = null,
            deliveryDateTime = null,
            approveDatetime = null,
            couponIds = couponIds,
            pointPrice = pointPrice,
            shippingCompany = shippingCompany,
            downloadDays = downloadDays,
            reasonRefund = null,
        )
    }
}