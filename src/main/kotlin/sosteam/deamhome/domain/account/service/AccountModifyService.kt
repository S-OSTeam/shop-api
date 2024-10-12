package sosteam.deamhome.domain.account.service

import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.exception.PwdAndConfirmPwdNotMatchedException
import sosteam.deamhome.domain.account.repository.AccountRepository

@Service
@RequiredArgsConstructor
class AccountModifyService(
	val accountRepository: AccountRepository,
	val passwordEncoder: PasswordEncoder
) {
	suspend fun changePwd(
		userId: String,
		newPwd: String,
		confirmPwd: String
	): String {
		if (newPwd != confirmPwd) throw PwdAndConfirmPwdNotMatchedException()
		
		val account = accountRepository.findAccountByUserId(userId)
			?: throw AccountNotFoundException()
		account.pwd = passwordEncoder.encode(newPwd)
		
		accountRepository.save(account)
		return account.userId
	}
}