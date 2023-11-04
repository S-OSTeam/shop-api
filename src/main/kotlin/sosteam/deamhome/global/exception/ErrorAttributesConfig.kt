package sosteam.deamhome.global.exception

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer

@Configuration
class ErrorAttributesConfig {
    @Bean
    fun defaultErrorAttributes():DefaultErrorAttributes{
        return DefaultErrorAttributes()
    }
    @Bean
    fun serverCodecConfigurer(): ServerCodecConfigurer {
        return ServerCodecConfigurer.create()
    }
}