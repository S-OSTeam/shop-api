package sosteam.deamhome.domain.auth.handler.request

import sosteam.deamhome.global.attribute.SNS

data class CheckDuplicateUserRequest(
	val email: String?,
	val userId: String?,
	val sns: SNS,
	val snsCode: String?
)