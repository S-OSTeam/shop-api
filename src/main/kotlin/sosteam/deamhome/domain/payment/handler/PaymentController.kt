package sosteam.deamhome.domain.payment.handler

import com.siot.IamportRestClient.IamportClient
import com.siot.IamportRestClient.request.CancelData
import com.siot.IamportRestClient.request.PrepareData
import com.siot.IamportRestClient.response.IamportResponse
import com.siot.IamportRestClient.response.Payment
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.payment.service.PaymentValidService
import java.math.BigDecimal
import java.util.*


@RestController
class PaymentController(
    private val paymentValidService: PaymentValidService,
    @Value("\${imp.api.key}")
    private val apiKey: String,
    @Value("\${imp.api.secretkey}")
    private val secretKey: String
) {
    private var iamportClient: IamportClient? = null

    @PostConstruct
    fun init() {
        iamportClient = IamportClient(apiKey, secretKey)
    }

    // 결제금액 사전등록
    @MutationMapping
    suspend fun createPrepareData(@Argument orderPublicId: Long): String {
        // order 조회해서 orderPrice
        // orderRepository.findByPublicId.price
        val amount = BigDecimal(10000)
        val merchantUid = UUID.randomUUID().toString()
        val prepareData = PrepareData(merchantUid, amount)
        iamportClient!!.postPrepare(prepareData)
        return merchantUid
    }

    suspend fun cancelPayment(response: IamportResponse<Payment>): CancelData {
        return CancelData(response.response.impUid, true)
    }

    @QueryMapping
    suspend fun validateIamport(@Argument imp_uid: String, @Argument orderPublicId: Long): Payment {
        // imp_uid 로 조회
        val iamportResponse: IamportResponse<Payment> = iamportClient!!.paymentByImpUid(imp_uid)
        val iamPortAmount = iamportResponse.response.amount.toInt()

        // order 조회해서 orderPrice
        // orderRepository.findByPublicId.price
        val dbAmount = 10000

        // 사후 조회
        // 가격이 다르면 취소
        if (dbAmount != iamPortAmount) {
            val cancelData = cancelPayment(iamportResponse)
            iamportClient!!.cancelPaymentByImpUid(cancelData)
        }

        return iamportResponse.response
        // 이후 유저 장바구니에서 삭제, 주문정보, 결제 정보 저장?? Order 를 저장??
        // 재고량 -1?
    }
}