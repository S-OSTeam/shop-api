package sosteam.deamhome.domain.delivery.handler.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.OffsetDateTime

class TrackInfo {
    @JsonProperty("trackingNumber")
    val trackingNumber: String = ""
    @JsonProperty("lastEvent")
    val lastEvent: TrackEvent = TrackEvent()
    @JsonProperty("events")
    val events: TrackEventConnection = TrackEventConnection()
}

class TrackEvent {
    @JsonProperty("status")
    val status: TrackEventStatus = TrackEventStatus()
    @JsonProperty("time")
    val time: OffsetDateTime = OffsetDateTime.MIN
    @JsonProperty("description")
    val description: String = ""
    @JsonProperty("location")
    val location: Location = Location()
}

class Location {
    @JsonProperty("countryCode")
    val countryCode: String = ""
    @JsonProperty("postalCode")
    val postalCode: String = ""
    @JsonProperty("name")
    val name: String = ""
}

class TrackEventStatus {
    @JsonProperty("code")
    val code: TrackEventStatusCode = TrackEventStatusCode.UNKNOWN
    @JsonProperty("name")
    val name: String = ""
}

class TrackEventConnection {
    @JsonProperty("edges")
    val edges: ArrayList<TrackEventEdge> = ArrayList()
}

class TrackEventEdge {
    @JsonProperty("node")
    val node: TrackEvent = TrackEvent()
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