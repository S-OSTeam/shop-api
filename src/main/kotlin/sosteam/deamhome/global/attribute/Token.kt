package sosteam.deamhome.global.attribute

enum class Token(val time: Long, val type: String) {
	ACCESS(
		1000 * 60 * 60 * 12,
		"accessToken"
	),
	REFRESH(
		1000 * 60 * 60 * 24 * 14,
		"refreshToken"
	);
}