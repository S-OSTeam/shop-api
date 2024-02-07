package sosteam.deamhome.global.entity

abstract class LogEntity(
	var ip: String = "127.0.0.1",
	var userAgent: String = "mozila",
	var referer: String = "http"
) : BaseEntity()