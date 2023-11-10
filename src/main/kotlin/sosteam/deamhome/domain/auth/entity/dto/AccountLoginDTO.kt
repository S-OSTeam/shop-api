package sosteam.deamhome.domain.auth.entity.dto

import org.springframework.security.core.authority.SimpleGrantedAuthority
import sosteam.deamhome.domain.account.entity.Account

data class AccountLoginDTO(
	val userId: String,
	val authorities: List<SimpleGrantedAuthority>
) {
	
	companion object {
		fun fromDomain(account: Account): AccountLoginDTO {
			return AccountLoginDTO(
				account.userId,
				account.getAuthorities()
			)
		}
	}
}