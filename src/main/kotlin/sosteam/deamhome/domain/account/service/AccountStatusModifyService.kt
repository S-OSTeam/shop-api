package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.global.attribute.Status
import java.time.LocalDateTime

@Service
@Transactional
@RequiredArgsConstructor
class AccountStatusModifyService(
	private val accountStatusRepository: AccountStatusRepository,
	private val accountRepository: AccountRepository,
//	private val reactiveMongoOperations: ReactiveMongoOperations,
) {

//	suspend fun updateAccountStatus(
//		userId: String,
//		status: Status,
//	): AccountStatus {
//		val accountStatus: AccountStatus =
//			accountStatusRepository.findByUserId(userId) ?: throw AccountNotFoundException()
//
//		if (status == Status.DORMANT) { // 휴면계정으로의 전환
//			val account: Account = accountRepository.findAccountByUserId(userId) ?: throw AccountNotFoundException()
//
//			reactiveMongoOperations.save(account, "accounts_dormant").awaitSingleOrNull()
//			accountRepository.delete(account)
//
//		} else if (status == Status.LIVE) { // 계정 활성화
//			val query = Query().addCriteria(Criteria.where("userId").`is`(userId))
//
//			var account: Account
//
//			if (accountStatus.status == Status.DORMANT) {
//				account = reactiveMongoOperations
//					.findOne(query, Account::class.java, "accounts_dormant")
//					.awaitSingleOrNull() ?: throw AccountNotFoundException()
//
//				reactiveMongoOperations
//					.remove(query, Account::class.java, "accounts_dormant")
//					.awaitSingleOrNull()
//			} else {
//				account = reactiveMongoOperations
//					.findOne(query, Account::class.java, "accounts_signout")
//					.awaitSingleOrNull() ?: throw AccountNotFoundException()
//
//				reactiveMongoOperations
//					.remove(query, Account::class.java, "accounts_signout")
//					.awaitSingleOrNull()
//			}
//
//			accountRepository.save(account)
//
//		} else if (status == Status.SIGNOUT) {
//			val account: Account = accountRepository.findAccountByUserId(userId) ?: throw AccountNotFoundException()
//
//			account.loginAt = LocalDateTime.now()
//			reactiveMongoOperations.save(account, "accounts_signout").awaitSingleOrNull()
//			accountRepository.delete(account)
//		}
//
//		accountStatus.status = status
//		accountStatusRepository.save(accountStatus) // accountStatus 상태 바꿔줌
//
//		return accountStatus
//	}
}