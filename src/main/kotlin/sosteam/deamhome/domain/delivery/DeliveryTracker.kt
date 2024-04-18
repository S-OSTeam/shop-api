package sosteam.deamhome.domain.delivery

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.graphql.client.HttpGraphQlClient
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit


@Component
class DeliveryTracker(
    @Value("\${deamhome.delivery.client.id}")
    private val clientId: String,
    @Value("\${deamhome.delivery.client.secret}")
    private val clientSecret: String
) {

    suspend fun getToken(): DeliveryTrackerAccessToken {

        val httpClient: HttpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) //timeout 시간 조절
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            }

        val webClient = WebClient.builder()
            .baseUrl("https://auth.tracker.delivery/oauth2/token")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()

        val bodyMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        bodyMap["grant_type"] = "client_credentials"
        bodyMap["client_id"] = clientId
        bodyMap["client_secret"] = clientSecret

        return webClient.post()
            .body(BodyInserters.fromFormData(bodyMap))
            .retrieve()
            .bodyToMono<DeliveryTrackerAccessToken>()
            .awaitSingle()
    }


    suspend fun getDeliveryStatus(token: DeliveryTrackerAccessToken, carrierId: String, trackingNumber: String): TrackEvent {

        val httpClient: HttpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) //timeout 시간 조절
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            }

        val webClient = WebClient.builder()
            .baseUrl("https://apis.tracker.delivery/graphql")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token.accessToken)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()

        val graphQlClient = HttpGraphQlClient.builder(webClient).build()

        val query =
            "query Track(\n" +
            "  \$carrierId: ID!,\n" +
            "  \$trackingNumber: String!\n" +
            ") {\n" +
            "  track(\n" +
            "    carrierId: \$carrierId,\n" +
            "    trackingNumber: \$trackingNumber\n" +
            "  ) {\n" +
            "    lastEvent {\n" +
            "      time\n" +
            "      status {\n" +
            "        code\n" +
            "        name\n" +
            "      }\n" +
            "      description\n" +
            "    }\n" +
            "  }\n" +
            "}"
        val variables = mapOf(
            "carrierId" to carrierId,
            "trackingNumber" to trackingNumber
        )
        return graphQlClient.document(query)
            .variables(variables)
            .retrieve("track")
            .toEntity(TrackInfo::class.java)
            .awaitSingle()
            .lastEvent
    }


}