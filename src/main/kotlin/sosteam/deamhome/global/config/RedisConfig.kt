package sosteam.deamhome.global.config

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession


@Suppress("DEPRECATION")
@Configuration
@RequiredArgsConstructor
@EnableRedisWebSession
class RedisConfig(
    @Value("\${spring.data.redis.host}")
    private val host: String,
    @Value("\${spring.data.redis.port}")
    private val port: Int,
    @Value("\${spring.data.redis.password}")
    private val password: String
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val lettuceConnectionFactory = LettuceConnectionFactory()
        host.also { lettuceConnectionFactory.hostName = it }
        port.also { lettuceConnectionFactory.port = it }
        lettuceConnectionFactory.setPassword(password)
        return lettuceConnectionFactory
    }
}