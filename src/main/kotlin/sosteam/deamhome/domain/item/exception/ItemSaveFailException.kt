package sosteam.deamhome.domain.item.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException
//TODO INTERNAL_ERROR 가 맞나요?
class ItemSaveFailException (
    errorCode: String = "ITEM_SAVE_FAIL",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "아이템 저장에 실패하였습니다."
) :
    CustomGraphQLException(errorCode, ErrorType.INTERNAL_ERROR, message) {
    override fun getMessage(): String = super.message
}