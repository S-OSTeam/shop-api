package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity

class LoginLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val success:Boolean,
    private val userId:String,
    private val reason:String,
    private val url:String
) : LogEntity(ip, userAgent, referer)