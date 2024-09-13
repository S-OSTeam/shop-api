package sosteam.deamhome.global.category.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class CategoryNotSupportedException(
    errorCode: String = "CATEGORY_NOT_SUPPORTED",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "지원하지 않는 카테고리입니다."
) : CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}
