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
			// 검증이 실패했을 때 null 반환하도록 설정
			val validationRules = ValidationRules.newValidationRules()
				.onValidationErrorStrategy(OnValidationErrorStrategy.RETURN_NULL)
				.build()
			// graphql 스키마로 validation 하도록 설정
			val schemaWiring = ValidationSchemaWiring(validationRules)
			// graphql Scalar 타입 설정
			wiringBuilder.scalar(ExtendedScalars.DateTime).scalar(ExtendedScalars.GraphQLLong)

			wiringBuilder.directiveWiring(schemaWiring)
		}
	}
}