package sosteam.deamhome.global.exception

import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import jakarta.validation.ConstraintViolationException
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.stereotype.Component

@Component
class CustomExceptionResolver : DataFetcherExceptionResolverAdapter() {

    override fun resolveToSingleError(ex: Throwable, env: DataFetchingEnvironment): GraphQLError? {
        return when (ex) {
            is CustomGraphQLException -> GraphqlErrorBuilder.newError()
                .errorType(ex.errorType)
                .message(ex.message)
                .extensions(ex.extensions)
                .location(env.field.sourceLocation)
                .build()
            is ConstraintViolationException -> {
                val violations = ex.constraintViolations
                if (violations.isNotEmpty()) {
                    val violationMessage = violations.first().message
                    GraphqlErrorBuilder.newError()
                        .message(violationMessage)
                        .location(env.field.sourceLocation)
                        .build()
                } else {
                    null
                }
            }
            else -> null
        }
    }
}