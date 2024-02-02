package sosteam.deamhome.global.mail

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service

@Service
class RandomStringService {
    fun generateKey(length: Int): String {
        return RandomStringUtils.random(length, true, true)
    }
}