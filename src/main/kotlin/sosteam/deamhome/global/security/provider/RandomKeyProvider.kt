package sosteam.deamhome.global.security.provider

import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class RandomKeyProvider {
	private val codeCharacterPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
	
	fun randomAlphabetNumber(len: Int): String {
		
		return (1..len)
			.map { Random.nextInt(0, codeCharacterPool.size) }
			.map { codeCharacterPool[it] }
			.joinToString("")
	}
}