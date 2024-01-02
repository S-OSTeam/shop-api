package sosteam.deamhome.global.config

import graphql.scalars.ExtendedScalars
import graphql.schema.idl.RuntimeWiring
import graphql.validation.rules.OnValidationErrorStrategy
import graphql.validation.rules.ValidationRules
import graphql.validation.schemawiring.ValidationSchemaWiring
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer


@Configuration
class GraphQLConfig {
	@Bean
	fun runtimeWiringConfigurer(): RuntimeWiringConfigurer {
		return RuntimeWiringConfigurer { wiringBuilder: RuntimeWiring.Builder ->
			val validationRules = ValidationRules.newValidationRules()
				.onValidationErrorStrategy(OnValidationErrorStrategy.RETURN_NULL)
				.build()

			val schemaWiring = ValidationSchemaWiring(validationRules)

			wiringBuilder.scalar(ExtendedScalars.DateTime).scalar(ExtendedScalars.GraphQLLong)

			wiringBuilder.directiveWiring(schemaWiring)
		}
	}
}