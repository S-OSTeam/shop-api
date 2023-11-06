package sosteam.deamhome.domain.account.service

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository

@Service
@RequiredArgsConstructor
class AccountValidService(private val accountRepository: AccountRepository) {
	
	//userId로 account 가져오기
	suspend fun getAccountByUserId(userId: String): Account? {
		return accountRepository.findAccountByUserId(userId)
	}
}