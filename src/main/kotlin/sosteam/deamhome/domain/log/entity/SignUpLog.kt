package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity

class SignUpLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val userId:String,
    private val recommendId:String
) : LogEntity(ip, userAgent, referer)