package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity

class ItemLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val title:String,
    private val content:String,
    private val summary:String,
    private val price:Int,
    private val status:Boolean,
    private val itemCategoryId:String,
    private val itemDetailCategoryId:String
) : LogEntity(ip, userAgent, referer)