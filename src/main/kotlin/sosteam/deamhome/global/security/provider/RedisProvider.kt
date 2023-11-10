package sosteam.deamhome.global.security.provider

import lombok.RequiredArgsConstructor
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
@RequiredArgsConstructor
class RedisProvider(val redisTemplate: StringRedisTemplate) {
	fun getData(key: String): String {
		val valueOperations = redisTemplate.opsForValue()
		return valueOperations.get(key).toString()
	}
	
	fun setData(key: String, value: String) {
		val valueOperations = redisTemplate.opsForValue()
		valueOperations.set(key, value)
	}
	
	fun setDataExpire(key: String, value: String, duration: Long) {
		val valueOperations = redisTemplate.opsForValue()
		val expireDuration = Duration.ofSeconds(duration)
		valueOperations.set(key, value, expireDuration)
	}
	
	fun deleteData(key: String) {
		redisTemplate.delete(key)
	}
}