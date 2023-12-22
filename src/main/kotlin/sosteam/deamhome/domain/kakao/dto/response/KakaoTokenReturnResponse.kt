package sosteam.deamhome.domain.kakao.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoTokenReturnResponse(
    @JsonProperty("access_token")
    val accessToken: String
)
