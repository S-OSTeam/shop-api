package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import lombok.RequiredArgsConstructor
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.global.attribute.Status

@Service
@RequiredArgsConstructor
class AccountStatusModifyService(
	private val accountStatusRepository: AccountStatusRepository,
	private val accountRepository: AccountRepository,
	private val reactiveMongoOperations: ReactiveMongoOperations,
) {
	
	suspend fun updateAccountStatus(
		userId: String,
		status: Status,
	): AccountStatus {
		val accountStatus: AccountStatus =
			accountStatusRepository.findByUserId(userId) ?: throw AccountNotFoundException()
		
		accountStatus.status = status
		accountStatusRepository.save(accountStatus).awaitSingle() // accountStatus 상태 바꿔줌
		
		if (status == Status.DORMANT) { // 휴면계정으로의 전환
			val account: Account = accountRepository.findAccountByUserId(userId) ?: throw AccountNotFoundException()
			
			reactiveMongoOperations.save(account, "accounts_dormant").awaitSingleOrNull()
			accountRepository.delete(account).awaitSingle()
			
		} else if (status == Status.LIVE) { // 계정 활성화
			val query = Query().addCriteria(Criteria.where("userId").`is`(userId))
			val account: Account = reactiveMongoOperations
				.findOne(query, Account::class.java, "accounts_dormant")
				.awaitSingleOrNull() ?: throw AccountNotFoundException()
			
			reactiveMongoOperations
				.remove(query, Account::class.java, "accounts_dormant")
				.awaitSingleOrNull()
			accountRepository.save(account).awaitSingle()
			
		}
		
		
		return accountStatus
	}
}