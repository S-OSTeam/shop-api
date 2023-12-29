package sosteam.deamhome.domain.naver.handler.response

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverTokenReturnResponse(
	@JsonProperty("access_token")
	val accessToken: String
)
