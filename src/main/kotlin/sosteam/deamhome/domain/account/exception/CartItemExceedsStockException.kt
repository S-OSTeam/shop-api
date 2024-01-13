package sosteam.deamhome.domain.account.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class CartItemExceedsStockException (
    errorCode: String = "CART_ITEM_EXCEEDS_STOCK_SIZE",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "재고 수량이 부족합니다."
) :
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}