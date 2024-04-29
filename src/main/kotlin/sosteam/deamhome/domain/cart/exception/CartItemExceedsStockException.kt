package sosteam.deamhome.domain.cart.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.domain.item.entity.Item
import sosteam.deamhome.global.exception.CustomGraphQLException

class CartItemExceedsStockException (
    errorCode: String = "CART_ITEM_EXCEEDS_STOCK_SIZE",
    item: Item?,
    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "재고 수량이 부족합니다. Item: ${item?.title}, 재고수량: ${item?.stockCnt}"
) :
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}