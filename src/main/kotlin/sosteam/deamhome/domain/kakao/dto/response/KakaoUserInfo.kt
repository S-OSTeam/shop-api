package sosteam.deamhome.domain.kakao.dto.response

import java.util.*

data class KakaoUserInfo (
    val id: Long? = null,
    val connected_at: Date? = null,
    val kakao_account: KaKaoAccount? = null
)