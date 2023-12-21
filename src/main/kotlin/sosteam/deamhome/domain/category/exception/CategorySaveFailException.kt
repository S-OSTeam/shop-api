package sosteam.deamhome.domain.category.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException
//TODO INTERNAL_ERROR 가 맞나요?
class CategorySaveFailException (
    errorCode: String = "CATEGORY_SAVE_FAIL",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "카테고리 저장에 실패하였습니다."
) :
    CustomGraphQLException(errorCode, ErrorType.INTERNAL_ERROR, message) {
    override fun getMessage(): String = super.message
}