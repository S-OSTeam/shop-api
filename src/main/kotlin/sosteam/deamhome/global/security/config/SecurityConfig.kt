package sosteam.deamhome.global.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
	
	@Bean
	fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
		http
			.authorizeExchange { exchanges ->
				exchanges
					.anyExchange().permitAll()
				
			}
			.csrf { it.disable() }
			.cors { it.disable() }
			.httpBasic { it.disable() }
		
		return http.build()
	}
	
	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}
}