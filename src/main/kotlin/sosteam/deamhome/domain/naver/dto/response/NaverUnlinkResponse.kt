package sosteam.deamhome.domain.naver.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverUnlinkResponse(
	@JsonProperty("result")
	val result: String,
	@JsonProperty("access_token")
	val accessToken: String
)