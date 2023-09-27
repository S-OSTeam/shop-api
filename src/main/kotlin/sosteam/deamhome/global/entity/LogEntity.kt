package sosteam.deamhome.global.entity

abstract class LogEntity(
	val ip: String = "127.0.0.1",
	val userAgent: String = "mozila",
	val referer: String = "http"
) : BaseEntity()