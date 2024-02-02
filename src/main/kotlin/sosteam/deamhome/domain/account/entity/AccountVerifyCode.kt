package sosteam.deamhome.domain.account.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import sosteam.deamhome.global.attribute.VerifyType
import java.io.Serializable

@RedisHash(value = "accountVerifyCode", timeToLive = 300)
data class AccountVerifyCode (

    @Indexed
    val email: String,

    @Id
    val VerifyCode: String,

    val type: VerifyType = VerifyType.SIGNUP,

    @TimeToLive
    val expiration : Long
): Serializable