package sosteam.deamhome.domain.account.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.auth.handler.request.SignUpRequest

@Service
class CreateAccountService(private val accountRepository: AccountRepository) {
	fun createAccount(signUpRequest: SignUpRequest) {
		
	
	}
}