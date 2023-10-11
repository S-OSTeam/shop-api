package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity

class ItemChangeLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val userId:String,
    private val title:String,
    private val content:String,
): LogEntity(ip, userAgent, referer)