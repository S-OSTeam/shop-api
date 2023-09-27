package sosteam.deamhome.global.attribute

import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
enum class Role(val role: String) {
	ROLE_GUEST("guest"), ROLE_USER("user"), ROLE_ADMIN("admin");
}