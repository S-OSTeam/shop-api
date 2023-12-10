package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity

class PointLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val content:String,
    private val point:Int,
    private val type:String,
    private val relatedId:String,
    private val action:String
) : LogEntity(ip, userAgent, referer)