package sosteam.deamhome.domain.naver.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverUserInfoResponse(
	@JsonProperty("response")
	val info: NaverUserInfo?,
	@JsonProperty("resultcode")
	val resultCode: String,
	@JsonProperty("message")
	val message: String
)
