package sosteam.deamhome.domain.log.entity

import sosteam.deamhome.global.entity.LogEntity

class ReIssueLog(
    ip:String,
    userAgent:String,
    referer:String,
) : LogEntity(ip, userAgent, referer)