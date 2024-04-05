package sosteam.deamhome.domain.payment.verifier

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.payment.exception.UnauthorizedSessionException
import sosteam.deamhome.global.aes.AES256
import sosteam.deamhome.global.log.ReactiveRequestContextHolder
import java.util.*

@RestController
class PaymentVerifier(
    private val aeS256: AES256
) {

    @MutationMapping
    suspend fun giveCode(): String {
        val session = ReactiveRequestContextHolder.getSession().awaitSingle()

        val code = UUID.randomUUID().toString()
        session.attributes.putIfAbsent("code", code)
        session.attributes.putIfAbsent("step", "give")

        return aeS256.encryptString(code)
    }

    @MutationMapping
    suspend fun takeCode(@Argument code: String): String {
        val session = ReactiveRequestContextHolder.getSession().awaitSingle()

        val step = session.attributes["step"] as String
        if (step != "give"){
            session.invalidate().awaitSingle()
            throw UnauthorizedSessionException()
        }

        // TODO 다른 알고리즘으로 변경해야함
        val decode = aeS256.decryptString(code)

        if (session.attributes["code"] == decode){
            session.attributes["step"] = "authorized"
        }
        else{
            session.invalidate().awaitSingle()
            throw UnauthorizedSessionException()
        }

        return "OK"
    }

}