package sosteam.deamhome.global.aes

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Component
class AES256(
    @Value("\${deamhome.aes256.secretkey}")
    private val secretKey: String
) {

    private val encoder = Base64.getEncoder()
    private val decoder = Base64.getDecoder()

    suspend fun encryptString(encryptString: String): String {

        val encryptedString = this.cipherPkcs5(Cipher.ENCRYPT_MODE, secretKey)
            .doFinal(encryptString.toByteArray(Charsets.UTF_8))

        return String(encoder.encode(encryptedString)) // base 64 encoding
    }

    suspend fun decryptString(decryptString: String): String {

        val byteString = decoder.decode(decryptString.toByteArray(Charsets.UTF_8)) // base 64 decoding
        return String(
            this.cipherPkcs5(Cipher.DECRYPT_MODE, secretKey).doFinal(byteString)
        ) // decryption
    }

    suspend fun cipherPkcs5(opMode: Int, secretKey: String): Cipher {
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val sk = SecretKeySpec(this.secretKey.toByteArray(Charsets.UTF_8), "AES")
        val iv = IvParameterSpec(this.secretKey.substring(0, 16).toByteArray(Charsets.UTF_8))

        c.init(opMode, sk, iv)
        return c
    }
}