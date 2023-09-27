package sosteam.deamhome.global.security.config

import jakarta.servlet.DispatcherType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import sosteam.deamhome.global.security.filter.LogFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
	private val logFilter: LogFilter
) {
	
	@Bean
	fun filterChain(http: HttpSecurity): SecurityFilterChain {
		http.csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
			.cors { obj: CorsConfigurer<HttpSecurity> -> obj.disable() }
			.addFilterBefore(
				logFilter,
				UsernamePasswordAuthenticationFilter::class.java
			)
//			.addFilterBefore(
//				jwtAuthFilter,
//				UsernamePasswordAuthenticationFilter::class.java
//			)
			.sessionManagement { sessionManagement: SessionManagementConfigurer<HttpSecurity?> ->
				sessionManagement.sessionCreationPolicy(
					SessionCreationPolicy.STATELESS
				)
			}
			.authorizeHttpRequests(Customizer { request ->
				request
					.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
					.requestMatchers(
						"/auth/**",
						"/user/signup",
						"/user/login",
						"/api-docs/**",
						"/swagger*/**",
						"/graphiql/**",
						"/graphql",
						"/back/graphiql/**",
						"/back/graphql"
					).permitAll()
					.anyRequest().authenticated()
			})
		return http.build()
	}
	
	@Bean
	fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}
}