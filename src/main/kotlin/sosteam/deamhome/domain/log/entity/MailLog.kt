package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity
import java.time.LocalDateTime

class MailLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val email:String,
    private val authCode:String,
    private val endAt:LocalDateTime
) : LogEntity(ip,userAgent,referer)