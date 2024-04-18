package sosteam.deamhome.domain.delivery

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

class TrackInfo {
    @JsonProperty("lastEvent")
    val lastEvent: TrackEvent = TrackEvent()
}

class TrackEvent {
    @JsonProperty("status")
    val status: TrackEventStatus = TrackEventStatus()
    @JsonProperty("time")
    val time: OffsetDateTime = OffsetDateTime.MIN
    @JsonProperty("description")
    val description: String = ""
}

class TrackEventStatus {
    @JsonProperty("code")
    val code: TrackEventStatusCode = TrackEventStatusCode.UNKNOWN
    @JsonProperty("name")
    val name: String = ""
}

enum class TrackEventStatusCode {
    UNKNOWN,
    INFORMATION_RECEIVED,
    AT_PICKUP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    ATTEMPT_FAIL,
    DELIVERED,
    AVAILABLE_FOR_PICKUP,
    EXCEPTION
}