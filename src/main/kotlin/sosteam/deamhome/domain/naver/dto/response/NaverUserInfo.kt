package sosteam.deamhome.domain.naver.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverUserInfo(
	@JsonProperty("id")
	val id: String? = null,
	@JsonProperty("gender")
	val gender: String? = null,
	@JsonProperty("email")
	val email: String? = null,
	@JsonProperty("name")
	val name: String? = null,
	@JsonProperty("birthday")
	val birthday: String? = null,
	@JsonProperty("birthyear")
	val birthyear: String? = null
)