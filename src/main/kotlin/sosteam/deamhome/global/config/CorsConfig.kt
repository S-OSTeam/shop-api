package sosteam.deamhome.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class CorsConfig : WebFluxConfigurer {
	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**")
			.allowedOrigins(
				"http://localhost:3000",
				"https://localhost:3000",
				"http://localhost:8080",
				"https://deamhome.synology.me"
			)
			.allowedOriginPatterns("*")
			.allowedMethods("GET", "POST", "PUT", "PATCH", "OPTIONS")
			.allowCredentials(true)
	}
}