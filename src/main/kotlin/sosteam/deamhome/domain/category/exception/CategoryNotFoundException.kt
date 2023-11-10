package sosteam.deamhome.domain.category.exception

import org.springframework.graphql.execution.ErrorType
import sosteam.deamhome.global.exception.CustomGraphQLException

class CategoryNotFoundException(
	errorCode: String = "CATEGORY_NOT_FOUND",
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String = "존재하지 않는 카테고리입니다."
) :
	CustomGraphQLException(errorCode, ErrorType.NOT_FOUND, message) {
	override fun getMessage(): String = super.message
}