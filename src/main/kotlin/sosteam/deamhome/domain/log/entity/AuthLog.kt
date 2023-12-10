package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity

class AuthLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val userId:String,
    private val type:String,
    private val success:Boolean
) : LogEntity(ip, userAgent, referer)