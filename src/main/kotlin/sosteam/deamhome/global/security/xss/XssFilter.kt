package sosteam.deamhome.global.security.xss

import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

@Component
class XssFilter : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request = exchange.request
        val modifiedRequest = XssRequestWrapper(request, exchange.response.bufferFactory())

        val modifiedExchange = exchange.mutate().request(modifiedRequest).build()

        return chain.filter(modifiedExchange)
    }

    private class XssRequestWrapper(
        delegate: ServerHttpRequest,
        private val bufferFactory: DataBufferFactory
    ) : ServerHttpRequestDecorator(delegate) {

        override fun getBody(): Flux<DataBuffer> {
            return super.getBody()
                .map { dataBuffer ->
                    val content = ByteArray(dataBuffer.readableByteCount())
                    dataBuffer.read(content)
                    DataBufferUtils.release(dataBuffer)

                    val body = String(content, StandardCharsets.UTF_8)
                    val escapedBody = escapeHtml(body)

                    val newContent = escapedBody.toByteArray(StandardCharsets.UTF_8)
                    bufferFactory.wrap(newContent)
                }
        }

        private fun escapeHtml(input: String): String {
            val escapedChars = mapOf(
                '<' to "&lt;",
                '>' to "&gt;",
                '\"' to "&quot;",
                '(' to "&#40;",
                ')' to "&#41;",
                '#' to "&#35;",
                '\'' to "&#39;"
            )

            val result = StringBuilder()
            for (char in input) {
                val escaped = escapedChars[char]
                result.append(escaped ?: char)
            }

            return result.toString()
        }
    }
}