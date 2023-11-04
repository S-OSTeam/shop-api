package sosteam.deamhome.global.exception

import org.slf4j.Logger
import org.springframework.boot.autoconfigure.web.*
import org.springframework.boot.autoconfigure.web.reactive.error.*
import org.springframework.boot.web.error.*
import org.springframework.boot.web.reactive.error.*
import org.springframework.context.*
import org.springframework.core.annotation.*
import org.springframework.http.*
import org.springframework.http.codec.*
import org.springframework.stereotype.*
import org.springframework.web.reactive.function.server.*
import sosteam.deamhome.global.provider.log



@Order(-2)
@Component
class GlobalExceptionHandler(
    errorAttributes: DefaultErrorAttributes,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer,
    var logger:Logger =log()
) : AbstractErrorWebExceptionHandler(errorAttributes, WebProperties.Resources(), applicationContext) {


    companion object {

        private const val HTTP_STATUS_KEY = "status" // http num
        private const val MESSAGE_KEY = "message" // 에러 메세지
        private const val ERRORS_KEY = "error" //
    }
    init {
        setMessageWriters(serverCodecConfigurer.writers)
    }

    override fun setMessageWriters(messageWriters: MutableList<HttpMessageWriter<*>>?) {
        super.setMessageWriters(messageWriters)
    }

    override public fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> {
        return RouterFunctions.route({ true }) { request ->
            val error: Throwable = getError(request)
            val attrs = getErrorAttributes(request, ErrorAttributeOptions.defaults())
            var httpStatus :HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR

            //Todo: graphql 추가 필요
            when (error) {

                is BaseException -> {

                    attrs[HTTP_STATUS_KEY] = error.errorCode.code
                    attrs[ERRORS_KEY] = error.errorCode.message
                    httpStatus = error.httpStatus
                    logger.warn(
                        """
                            {
                                "code" : "${error.errorCode.code}",
                                "message" : "${error.errorCode.message}",
                                "status" : "$httpStatus"
                            }
                        """.trimIndent()
                    )
                    ServerResponse
                        .status(httpStatus)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(attrs)
                }
                else -> {

                    // 기본 오류 처리 로직
                    attrs[HTTP_STATUS_KEY] = HttpStatus.INTERNAL_SERVER_ERROR
                    logger.warn (
                        """
                            {
                                "message" : "${error.message?.replace("\"", "'")}",
                                "status" : "$httpStatus"
                            }
                        """.trimIndent()
                    )

                    ServerResponse
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(attrs)
                }
            }
        }
    }



}