package sosteam.deamhome.global.log

import org.springframework.http.HttpHeaders
import reactor.core.publisher.Mono
import sosteam.deamhome.global.entity.LogEntity

class LogUtil {
    companion object {
        fun getDefaultLog() = ReactiveRequestContextHolder.getRequest()
            .flatMap { request ->
                val headers: HttpHeaders = request.headers
                val ipAddress = headers.getFirst("X-Forwarded-For") ?: request.remoteAddress?.address?.hostAddress!!
                Mono.just(
                    InitLog(
                        ipAddress,
                        headers.getFirst("User-Agent")!!,
                        headers.getFirst("Referer") ?: "Check Referer Policy"
                    )
                )
            }
    }
}