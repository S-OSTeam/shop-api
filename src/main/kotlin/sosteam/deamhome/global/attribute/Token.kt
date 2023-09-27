package sosteam.deamhome.global.attribute

import lombok.Getter

@Getter
enum class Token(time: Int, type: String) {
	ACCESS(
		1000 * 60 * 60 * 12,
		"accessToken"
	),
	REFRESH(
		1000 * 60 * 60 * 24 * 14,
		"refreshToken"
	);
	
	private val milliseconds: Long
	private val type: String
	
	init {
		milliseconds = time.toLong()
		this.type = type
	}
}