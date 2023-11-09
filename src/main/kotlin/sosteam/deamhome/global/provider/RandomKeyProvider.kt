package sosteam.deamhome.global.provider

import java.security.SecureRandom

class RandomKeyProvider {
    private val upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val lower = upper.lowercase()
    private val digits = "0123456789"
    private val alphaNum = upper + lower + digits

    private val secureRandom = SecureRandom()

    fun randomAlphabetNumber(length: Int): String {
        return (1..length)
            .map { alphaNum[secureRandom.nextInt(alphaNum.length)] }
            .joinToString("")
    }
}