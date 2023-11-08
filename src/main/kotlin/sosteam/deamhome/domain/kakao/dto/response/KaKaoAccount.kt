package sosteam.deamhome.domain.kakao.dto.response

data class KaKaoAccount (
    val profile: KakaoProfile? = null,
    val is_email_valid: Boolean? = null,
    val is_email_verified: Boolean? = null,
    val email: String? = null
)