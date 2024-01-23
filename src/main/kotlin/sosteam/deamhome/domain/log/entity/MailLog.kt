package sosteam.deamhome.domain.log.entity

import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.LogEntity
import java.time.LocalDateTime

@Table
class MailLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val email:String,
    private val authCode:String,
    private val endAt:LocalDateTime
) : LogEntity(ip,userAgent,referer)