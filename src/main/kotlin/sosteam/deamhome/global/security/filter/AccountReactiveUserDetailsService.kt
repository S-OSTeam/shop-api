package sosteam.deamhome.global.security.filter

import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.repository.AccountRepository

@Service
class AccountReactiveUserDetailsService(
	private val accountRepository: AccountRepository
) : ReactiveUserDetailsService {
	override fun findByUsername(username: String?): Mono<UserDetails> = mono {
		val account = accountRepository.findAccountByUserId(username!!) ?: throw BadCredentialsException("error")
		return@mono User(account.userId, null, account.getAuthorities())
	}
}