package sosteam.deamhome.domain.category.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class AlreadyExistCategoryException (
    errorCode: String = "ALREADY_EXIST_CATEGORY",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "이미 존재하는 카테고리입니다."
) :
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}