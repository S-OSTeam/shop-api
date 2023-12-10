package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity

class SearchLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val keyword:String,
    private val userId:String,
) : LogEntity(ip, userAgent, referer)