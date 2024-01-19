package sosteam.deamhome.domain.account.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class CartCheckListInvalidException (
    errorCode: String = "CART_CHECKLIST_INVALID_EXCEPTION",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "유효하지 않은 체크리스트 변화입니다."
) :
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}