package sosteam.deamhome.global.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.crypto.password.PasswordEncoder

@TestConfiguration
class TestPasswordEncoderConfig {
	
	@Primary
	@Bean
	fun testPasswordEncoder() =
		object : PasswordEncoder {
			override fun encode(rawPassword: CharSequence) =
				rawPassword.toString()
			
			override fun matches(rawPassword: CharSequence, encodedPassword: String) =
				rawPassword == encodedPassword
			
			override fun upgradeEncoding(encodedPassword: String) = false
		}
}