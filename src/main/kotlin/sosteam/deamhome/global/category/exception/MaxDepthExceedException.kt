package sosteam.deamhome.global.category.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class MaxDepthExceedException (
    errorCode: String = "MAX_DEPTH_EXCEED",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "카테고리의 최대 깊이를 초과하였습니다."
) :
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}