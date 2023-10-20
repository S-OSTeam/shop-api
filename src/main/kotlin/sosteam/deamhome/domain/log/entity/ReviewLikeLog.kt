package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity

class ReviewLikeLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val reviewId:String,
    private val userId:String
) : LogEntity(ip, userAgent, referer)