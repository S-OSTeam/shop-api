package sosteam.deamhome.domain.delivery

import com.fasterxml.jackson.annotation.JsonProperty

class DeliveryTrackerAccessToken(
    @JsonProperty("access_token")
    val accessToken: String = "",
    @JsonProperty("expires_in")
    val expiresIn: String = "",
    @JsonProperty("token_type")
    val tokenType: String = ""
) {
}