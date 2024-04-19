package sosteam.deamhome.domain.delivery.service

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.graphql.client.FieldAccessException
import org.springframework.graphql.client.HttpGraphQlClient
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import sosteam.deamhome.domain.delivery.exception.DeliveryTrackerApiException
import sosteam.deamhome.domain.delivery.handler.DeliveryTrackerAccessToken
import sosteam.deamhome.domain.delivery.handler.response.TrackEvent
import sosteam.deamhome.domain.delivery.handler.response.TrackInfo
import java.time.Duration
import java.util.concurrent.TimeUnit


@Service
@Transactional
class DeliveryTrackerService(
    @Value("\${deamhome.delivery.client.id}")
    private val clientId: String,
    @Value("\${deamhome.delivery.client.secret}")
    private val clientSecret: String
) {

    private suspend fun getHttpClient(): HttpClient {
        return HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) //timeout 시간 조절
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            }
    }

    private suspend fun getWebClient(httpClient: HttpClient, url: String, token: DeliveryTrackerAccessToken?): WebClient {
        val builder = WebClient.builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

        token?.let { builder.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer ${it.accessToken}") }

        return builder.clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }

    suspend fun getToken(): DeliveryTrackerAccessToken {

        val webClient = getWebClient(
            httpClient = getHttpClient(),
            url = "https://auth.tracker.delivery/oauth2/token",
            token = null
        )

        val bodyMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        bodyMap["grant_type"] = "client_credentials"
        bodyMap["client_id"] = clientId
        bodyMap["client_secret"] = clientSecret

        return webClient.post()
            .body(BodyInserters.fromFormData(bodyMap))
            .retrieve()
            .onStatus({ status -> status.is4xxClientError or status.is5xxServerError }) { clientResponse ->
                clientResponse.bodyToMono(String::class.java)
                    .flatMap { errorMessage ->
                        Mono.error(DeliveryTrackerApiException(message = errorMessage))
                    }
            }
            .bodyToMono<DeliveryTrackerAccessToken>()
            .awaitSingle()
    }


    suspend fun getLastTrack(token: DeliveryTrackerAccessToken, carrierId: String, trackingNumber: String): TrackEvent {

        val webClient = getWebClient(
            httpClient = getHttpClient(),
            url = "https://apis.tracker.delivery/graphql",
            token = token
        )

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

        try {
            return graphQlClient.document(query)
                .variables(variables)
                .retrieve("track")
                .toEntity(TrackInfo::class.java)
                .awaitSingle()
                .lastEvent
        } catch (ex: FieldAccessException) {
            val (message, code) = extractMessageAndCode(ex.message!!)
            throw DeliveryTrackerApiException(message = message?:"", errorCode = code?:"")
        }
    }

    suspend fun getTracks(token: DeliveryTrackerAccessToken, carrierId: String, trackingNumber: String, last: Int): TrackInfo {

        val webClient = getWebClient(
            httpClient = getHttpClient(),
            url = "https://apis.tracker.delivery/graphql",
            token = token
        )

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
            "    trackingNumber\n" +
            "    lastEvent {\n" +
            "      time\n" +
            "      status {\n" +
            "        code\n" +
            "        name\n" +
            "      }\n" +
            "      location{\n" +
            "        name\n" +
            "        countryCode\n" +
            "        postalCode\n" +
            "      }\n" +
            "      description\n" +
            "    }\n" +
            "    events(last: $last) {\n" +
            "      edges {\n" +
            "        node {\n" +
            "          time\n" +
            "          status {\n" +
            "            code\n" +
            "            name\n" +
            "          }\n" +
            "          description\n" +
            "          location{\n" +
            "            name\n" +
            "            countryCode\n" +
            "            postalCode\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}"
        val variables = mapOf(
            "carrierId" to carrierId,
            "trackingNumber" to trackingNumber
        )

        try {
            return graphQlClient.document(query)
                .variables(variables)
                .retrieve("track")
                .toEntity(TrackInfo::class.java)
                .awaitSingle()
        } catch (ex: FieldAccessException) {
            val (message, code) = extractMessageAndCode(ex.message!!)
            throw DeliveryTrackerApiException(message = message?:"", errorCode = code?:"")
        }
    }

    suspend fun extractMessageAndCode(response: String): Pair<String?, String?> {
        val messageRegex = Regex("message=(.*?),")
        val messageMatchResult = messageRegex.find(response)
        val message = messageMatchResult?.groupValues?.get(1)

        val codeRegex = Regex("code=(.*?)\\}")
        val codeMatchResult = codeRegex.find(response)
        val code = codeMatchResult?.groupValues?.get(1)

        return Pair(message, code)
    }


}