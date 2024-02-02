package sosteam.deamhome.domain.account.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import sosteam.deamhome.global.attribute.VerifyType
import java.io.Serializable

@RedisHash(value = "accountVerifyCode", timeToLive = 300)
data class AccountVerifyCode (

    @Id
    val emailAndVerifyCode: String,

    val type: VerifyType = VerifyType.SIGNUP,

    @TimeToLive
    val expiration : Long
): Serializable