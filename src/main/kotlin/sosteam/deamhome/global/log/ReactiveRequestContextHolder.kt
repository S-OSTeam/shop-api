package sosteam.deamhome.global.log

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebSession
import reactor.core.publisher.Mono
import reactor.util.context.ContextView
import java.util.function.Function


class ReactiveRequestContextHolder {
    companion object {
        val CONTEXT_KEY : Class<ServerHttpRequest> = ServerHttpRequest::class.java
        fun getRequest(): Mono<ServerHttpRequest> {
            return Mono.deferContextual { contextView ->
                Mono.just(contextView.get(CONTEXT_KEY))
            }
        }

        fun getSession(): Mono<WebSession> {
            return Mono.deferContextual { data: ContextView? ->
                Mono.just(data!!)
            }
                .map { contextView: ContextView ->
                    contextView.get(ServerWebExchange::class.java).session
                }
                .flatMap(Function.identity())
        }

    }
}