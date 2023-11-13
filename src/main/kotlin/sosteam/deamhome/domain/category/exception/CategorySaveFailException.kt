package sosteam.deamhome.domain.category.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException
//TODO NTERNAL_ERROR 가 맞나요?
class CategorySaveFailException (
    errorCode: String = "CATEGORY_NOT_FOUND",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "존재하지 않는 카테고리입니다."
) :
    CustomGraphQLException(errorCode, ErrorType.INTERNAL_ERROR, message) {
    override fun getMessage(): String = super.message
}