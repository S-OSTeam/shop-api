package sosteam.deamhome.domain.account.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class WishListOverflowException(
    errorCode: String = "WISHLIST_OVERFLOW",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "위시리스트의 최대 개수(100개)를 초과했습니다."
) :
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}