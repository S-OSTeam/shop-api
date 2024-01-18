package sosteam.deamhome.global.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.header.XXssProtectionServerHttpHeadersWriter
import reactor.core.publisher.Mono
import sosteam.deamhome.global.security.filter.TokenAuthFilter

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(val tokenAuthFilter: TokenAuthFilter) {
	
	@Bean
	fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
		http
			.exceptionHandling { exceptionHandlingSpec ->
				exceptionHandlingSpec
					.authenticationEntryPoint { swe, e ->
						Mono.fromRunnable { swe.response.statusCode = HttpStatus.UNAUTHORIZED }
					}
					.accessDeniedHandler { swe, e ->
						Mono.fromRunnable { swe.response.statusCode = HttpStatus.FORBIDDEN }
					}
			}
			.authorizeExchange { exchanges ->
				exchanges
					.anyExchange().permitAll()
			}
			.csrf { it.disable() }
			.cors { it.disable() }
			.httpBasic { it.disable() }
			.headers { headers ->
				headers.xssProtection { xss ->
					// Disabled?
					xss.headerValue(XXssProtectionServerHttpHeadersWriter.HeaderValue.DISABLED)
						.contentSecurityPolicy { csp ->
							csp.policyDirectives("default-src 'self'; script-src 'self'; img-src 'self';")
						}
				}
			}
			//.addFilterAt(tokenAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
		
		return http.build()
	}
	
	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}
}