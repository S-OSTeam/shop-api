package sosteam.deamhome.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringDocConfig {
	@Bean
	fun openAPI(@Value("\${springdoc.version}") springdocVesion: String?): OpenAPI {
		val info = Info()
			.title("DeamHome api 서버")
			.version(springdocVesion)
			.description("DeamHome의 서버 swagger 입니다.")
		return OpenAPI()
			.components(Components())
			.info(info)
	}
}