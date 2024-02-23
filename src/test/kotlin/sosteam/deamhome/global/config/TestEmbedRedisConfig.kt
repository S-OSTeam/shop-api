package sosteam.deamhome.global.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import redis.embedded.RedisServer

@TestConfiguration
@EnableRedisRepositories
class TestEmbedRedisConfig {
	private val redisHost = "127.0.0.1"
	private val redisPort = 6379
	private val redisServer = RedisServer(redisPort)
	
	@PostConstruct
	fun startRedisServer() {
		redisServer.start()
	}
	
	@PreDestroy
	fun stopRedisServer() {
		redisServer.stop()
	}
	
	@Primary
	@Bean
	fun redisConnectionFactory() = LettuceConnectionFactory(redisHost, redisPort)
	
	@Primary
	@Bean
	fun redisTemplate(): RedisTemplate<String, String> {
		val redisTemplate = RedisTemplate<String, String>()
		redisTemplate.connectionFactory = redisConnectionFactory()
		return redisTemplate
	}
}