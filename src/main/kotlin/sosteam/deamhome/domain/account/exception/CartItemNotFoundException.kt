package sosteam.deamhome.domain.account.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class CartItemNotFoundException (
    errorCode: String = "CART_ITEM_NOT_FOUND",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "존재하지 않는 장바구니 아이템입니다"
) :
    CustomGraphQLException(errorCode, ErrorType.NOT_FOUND, message) {
    override fun getMessage(): String = super.message
}