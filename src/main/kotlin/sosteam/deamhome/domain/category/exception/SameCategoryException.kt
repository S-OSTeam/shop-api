package sosteam.deamhome.domain.category.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class SameCategoryException (
    errorCode: String = "SAME_CATEGORY",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "같은 카테고리 입니다."
) :
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}