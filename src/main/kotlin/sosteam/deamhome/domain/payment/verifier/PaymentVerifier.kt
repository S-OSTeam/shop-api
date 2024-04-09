package sosteam.deamhome.domain.payment.verifier

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.payment.exception.UnauthorizedSessionException
import sosteam.deamhome.global.aes.AES256
import sosteam.deamhome.global.argon.Argon
import sosteam.deamhome.global.log.ReactiveRequestContextHolder
import java.util.*

@RestController
class PaymentVerifier(
    private val aeS256: AES256,
    private val argon: Argon
) {

    suspend fun generateCode(): String {
        val session = ReactiveRequestContextHolder.getSession().awaitSingle()

        val code = session.attributes.computeIfAbsent("code") { UUID.randomUUID().toString() } as String
        session.attributes.putIfAbsent("step", "give")

        return aeS256.encryptString(code)
    }


    suspend fun verifyCode(code: String): Boolean {
        val session = ReactiveRequestContextHolder.getSession().awaitSingle()

        // 이전 단계를 거쳤는지 확인
        val step = session.attributes["step"] as String?
        println("step = $step")
        if (step != "give"){
            val awaitSingleOrNull = session.invalidate().awaitSingleOrNull()
            println(awaitSingleOrNull)
            throw UnauthorizedSessionException()
        }

        val rawCode = session.attributes["code"] as String
        if (argon.isMatch(rawCode, code)){
            session.attributes["step"] = "verified"
        }
        else{
            session.invalidate().awaitSingleOrNull()
            throw UnauthorizedSessionException()
        }

        return true
    }

    suspend fun isVerified(): Boolean {
        val session = ReactiveRequestContextHolder.getSession().awaitSingle()
        return session.attributes["step"] == "verified"
    }

}