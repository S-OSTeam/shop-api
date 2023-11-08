package sosteam.deamhome.domain.kakao.dto.response

data class KakaoTokenReturnResponse(
    val token_type: String? = null,
    val access_token: String? = null,
    val expires_in: Int? = null,
    val refresh_token: String? = null,
    val refresh_token_expires_in: Int? = null,
    val scope: String? = null
)
