package sosteam.deamhome.global.category.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class CategoryDeleteFailException (
    errorCode: String = "CATEGORY_DELETE_FAIL",

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String = "카테고리 삭제에 실패하였습니다."
) :
    CustomGraphQLException(errorCode, ErrorType.BAD_REQUEST, message) {
    override fun getMessage(): String = super.message
}