package sosteam.deamhome.domain.delivery.handler

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.delivery.handler.request.DeliveryRequest
import sosteam.deamhome.domain.delivery.service.DeliveryTrackerService
import sosteam.deamhome.domain.delivery.handler.response.TrackEvent
import sosteam.deamhome.domain.delivery.handler.response.TrackInfo

@RestController
class DeliveryResolver(
    private val deliveryTrackerService: DeliveryTrackerService
) {
    private var cachedToken: DeliveryTrackerAccessToken? = null
    private var tokenTimestamp: Long = 0

    @QueryMapping
    suspend fun getLastTrack(@Argument request: DeliveryRequest): TrackEvent {
        val token = getToken()
        return deliveryTrackerService.getLastTrack(token, request.carrierId, request.trackingNumber)
    }

    @QueryMapping
    suspend fun getAllTracks(@Argument request: DeliveryRequest): TrackInfo {
        val token = getToken()
        return deliveryTrackerService.getTracks(token, request.carrierId, request.trackingNumber, request.last)
    }

    private suspend fun getToken(): DeliveryTrackerAccessToken {
        // 이전 토큰이 유효하고 만료되지 않았으면 이전 토큰을 반환
        if (cachedToken != null && System.currentTimeMillis() - tokenTimestamp < 3580000) {
            return cachedToken!!
        }
        // 새로운 토큰을 가져옴
        val newToken = deliveryTrackerService.getToken()
        cachedToken = newToken
        tokenTimestamp = System.currentTimeMillis()
        return newToken
    }

}