package sosteam.deamhome.global.security.xss

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class WebFluxConfig : WebFluxConfigurer {
    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        val objectMapper = ObjectMapper()
        objectMapper.factory.characterEscapes = HtmlCharacterEscape()
        configurer.customCodecs().registerWithDefaultConfig(Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))
//        configurer.customCodecs().decoder(Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON))
    }
}