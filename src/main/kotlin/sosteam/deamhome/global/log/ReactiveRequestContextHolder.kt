package sosteam.deamhome.global.log

import org.springframework.http.server.reactive.ServerHttpRequest
import reactor.core.publisher.Mono

class ReactiveRequestContextHolder {
    companion object {
        val CONTEXT_KEY : Class<ServerHttpRequest> = ServerHttpRequest::class.java
        fun getRequest(): Mono<ServerHttpRequest> {
            return Mono.deferContextual { contextView ->
                Mono.just(contextView.get(CONTEXT_KEY))
            }
        }
    }
}