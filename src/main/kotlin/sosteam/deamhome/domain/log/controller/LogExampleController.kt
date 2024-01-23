package sosteam.deamhome.domain.log.controller

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.log.entity.CartLog
import sosteam.deamhome.domain.log.entity.MailLog
import sosteam.deamhome.domain.log.repository.CartLogRepository
import sosteam.deamhome.domain.log.repository.MailLogRepository
import sosteam.deamhome.global.log.LogUtil
import java.time.LocalDateTime

@Controller
class LogExampleController (
    private val mailLogRepository: MailLogRepository,
    private val cartLogRepository: CartLogRepository
){
    // TODO
//    @QueryMapping("mailLog")
//    fun mailLogExample(): Mono<String> {
//        return LogUtil.getDefaultLog()
//            .flatMap { init ->
//                val mailLog = MailLog(
//                    init.ip ,
//                    init.userAgent,
//                    init.referer,
//                    "example@naver.com",
//                    "01294",
//                    LocalDateTime.now().plusMinutes(10)
//                )
//                mailLogRepository.save(mailLog)
//            }
//            .map { it ->
//                it.toString()
//            }
//    }
//
//    @QueryMapping("cartLog")
//    fun cartLogExample(): Mono<String> {
//        return LogUtil.getDefaultLog()
//            .flatMap { init ->
//                val cartLog = CartLog(
//                    init.ip,
//                    init.userAgent,
//                    init.referer,
//                    10000,
//                    2000,
//                    8000,
//                    3,
//                    "This is ItemId",
//                    "This is AccountId"
//                )
//                cartLogRepository.save(cartLog)
//            }
//            .map { it ->
//                it.toString()
//            }
//    }
}