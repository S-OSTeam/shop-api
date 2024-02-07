package sosteam.deamhome.global.entity

import org.springframework.data.relational.core.mapping.Column

abstract class LogEntity(
	var ip: String = "127.0.0.1",
	@Column("user_agent")
	var userAgent: String = "mozila",
	var referer: String = "http"
) : BaseEntity()