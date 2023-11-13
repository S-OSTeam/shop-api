package sosteam.deamhome.global.exception

import graphql.GraphqlErrorException
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import org.springframework.stereotype.Component

@Component
class CustomDataFetcherExceptionHandler : DataFetcherExceptionHandler {
	override fun onException(handlerParameters: DataFetcherExceptionHandlerParameters?): DataFetcherExceptionHandlerResult {
		val exception = handlerParameters?.exception
		val sourceLocation = handlerParameters?.sourceLocation
		val path = handlerParameters?.path

		println("hihihihih------------------------hihihih")



		val error = when (exception) {
			is CustomGraphQLException ->
				GraphqlErrorException.newErrorException()
					.errorClassification(exception.errorType)
					.message(exception.message)
					.extensions(exception.extensions)
					.build()
			
			else ->
				null
		}
		
		return DataFetcherExceptionHandlerResult.newResult().error(error).build()
	}
}