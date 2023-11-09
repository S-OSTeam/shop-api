package sosteam.deamhome.global.exception

import graphql.ErrorClassification
import graphql.GraphQLError
import graphql.language.SourceLocation
import org.springframework.graphql.execution.ErrorType

abstract class CustomGraphQLException(
	private val errorCode: String = "",
	
	private val errorType: ErrorType,
	
	@JvmField
	@Suppress("INAPPLICABLE_JVM_FIELD")
	override val message: String,
) : RuntimeException(message), GraphQLError {
	override fun getMessage(): String = message
	
	override fun getLocations(): MutableList<SourceLocation>? = null
	
	override fun getErrorType(): ErrorClassification? = errorType
	
	override fun getExtensions(): MutableMap<String, Any> {
		return mutableMapOf(
			Pair("code", this.errorCode),
			Pair("exception", this.javaClass.simpleName)
		)
	}
}