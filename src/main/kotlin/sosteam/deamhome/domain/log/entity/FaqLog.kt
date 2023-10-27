package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity

class FaqLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val title:String,
    private val content:String,
    private val answer:String,
    private val userId:String,
    private val categoryId:String
) : LogEntity(ip, userAgent, referer)