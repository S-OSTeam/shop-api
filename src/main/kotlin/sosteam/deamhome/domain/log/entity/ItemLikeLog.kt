package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity

class ItemLikeLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val itemId:String,
    private val userId:String
) : LogEntity(ip, userAgent, referer)