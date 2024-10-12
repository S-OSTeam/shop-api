package sosteam.deamhome.global.attribute

enum class Token(val time: Long, val type: String) {
	ACCESS(
		60 * 60 * 4,
		"accessToken"
	),
	REFRESH(
		60 * 60 * 24 * 14,
		"refreshToken"
	)
}