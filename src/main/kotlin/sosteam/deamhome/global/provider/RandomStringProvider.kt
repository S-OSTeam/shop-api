package sosteam.deamhome.global.provider

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service

@Service
class RandomStringProvider {
    fun generateKey(length: Int): String {
        return RandomStringUtils.random(length, true, true)
    }
}