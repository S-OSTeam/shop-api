package sosteam.deamhome.domain.kakao.dto.request

data class KakaoLoginRequest (
    val code: String,
    val state: String
)