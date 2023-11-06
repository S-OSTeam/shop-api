package sosteam.deamhome.domain.account.service

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.exception.AlreadyExistAccountException
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.global.attribute.SNS

@Service
@RequiredArgsConstructor
class AccountStatusValidService(
	private val accountStatusRepository: AccountStatusRepository,
) {
	suspend fun getStatusByUserIdOrSNS(userId: String, sns: SNS, snsId: String): AccountStatus? {
		return accountStatusRepository.getStatusByUserIdOrSNS(userId, sns, snsId)
	}
	
	suspend fun isAlreadyExistAccount(userId: String, sns: SNS, snsId: String): Boolean {
		val accountStatus = getStatusByUserIdOrSNS(userId, sns, snsId)
		
		if (accountStatus != null)
			throw AlreadyExistAccountException()
		
		return true
	}
}