package sosteam.deamhome.domain.log.entity

import org.springframework.data.relational.core.mapping.Table
import sosteam.deamhome.global.entity.LogEntity

@Table
class CartLog(
    ip:String,
    userAgent:String,
    referer:String,
    private val fullPrice:Int,
    private val discountPrice:Int,
    private val realPrice:Int,
    private val cnt:Int,
    private val itemId:String,
    private val userId:String
) : LogEntity(ip, userAgent, referer)