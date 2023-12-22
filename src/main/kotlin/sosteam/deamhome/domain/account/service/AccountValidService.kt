package sosteam.deamhome.domain.account.service

import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.exception.LoginFailureException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.auth.entity.dto.AccountLoginDTO
import sosteam.deamhome.domain.auth.handler.request.AccountCreateRequest
import sosteam.deamhome.global.exception.PasswordNotMatchedException

@Service
@RequiredArgsConstructor
class AccountValidService(
	private val accountRepository: AccountRepository,
	private val passwordEncoder: PasswordEncoder
) {
	
	//userId로 account 가져오기
	suspend fun getAccountByUserId(userId: String): Account? {
		return accountRepository.findAccountByUserId(userId)
	}
	
	suspend fun getAccountLoginDTO(id: String, pwd: String): AccountLoginDTO {
		val account = accountRepository.findAccountById(id) ?: throw LoginFailureException()
		
		if (!passwordEncoder.matches(pwd, account.pwd)) {
			throw LoginFailureException()
		}
		
		return AccountLoginDTO.fromDomain(account)
	}
	
	suspend fun getAccountByUserName(userName: String): Account? {
		return accountRepository.findAccountByUserName(userName)
	}
	
	suspend fun isValidAccount(accountCreateRequest: AccountCreateRequest): Boolean {
		if (accountCreateRequest.pwd != accountCreateRequest.confirmPwd)
			throw PasswordNotMatchedException()
		
		return true
	}
	
	
}