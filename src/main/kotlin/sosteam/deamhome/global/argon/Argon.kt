package sosteam.deamhome.global.argon

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.stereotype.Component

@Component
class Argon(
    @Value("\${deamhome.argon.saltLength}")
    private val saltLength: Int,
    @Value("\${deamhome.argon.hashLength}")
    private val hashLength: Int,
    @Value("\${deamhome.argon.parallelism}")
    private val parallelism: Int,
    @Value("\${deamhome.argon.memory}")
    private val memory: Int,
    @Value("\${deamhome.argon.iterations}")
    private val iterations: Int
) {
    private val argon2PasswordEncoder: Argon2PasswordEncoder = Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations)

    suspend fun isMatch(raw:String, code:String): Boolean {
        return argon2PasswordEncoder.matches(raw, code)
    }

    suspend fun encode(raw:String): String {
        return argon2PasswordEncoder.encode(raw)
    }

}