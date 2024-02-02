package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.exception.AccountDeletedException
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.global.attribute.Status
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period

@Service
@Transactional
@RequiredArgsConstructor
class AccountStatusModifyService(
	private val accountStatusRepository: AccountStatusRepository,
	private val accountRepository: AccountRepository,
//	private val reactiveMongoOperations: ReactiveMongoOperations,
) {

	// TODO MongoOperation 에서 postgreSQL 로 바꾼 뒤 주석 해제
	/*suspend fun updateAccountStatus(
		userId: String,
		status: Status,
	): AccountStatus {
		val accountStatus: AccountStatus =
			accountStatusRepository.findByUserId(userId) ?: throw AccountNotFoundException()

		if(Period.between(accountStatus.deletedAT.toLocalDate(), LocalDate.now()).days > 14) throw AccountDeletedException()

		if (status == Status.LIVE) { // 계정 활성화
			val query = Query().addCriteria(Criteria.where("userId").`is`(userId))

			var account: Account

			if (accountStatus.status == Status.SIGNOUT) {
				account = reactiveMongoOperations
					.findOne(query, Account::class.java, "accounts_dormant")
					.awaitSingleOrNull() ?: throw AccountNotFoundException()

				reactiveMongoOperations
					.remove(query, Account::class.java, "accounts_dormant")
					.awaitSingleOrNull()
			}
			accountStatus.deletedAT = null
		} else if (status == Status.SIGNOUT) {
			val account: Account = accountRepository.findAccountByUserId(userId) ?: throw AccountNotFoundException()

			account.loginAt = LocalDateTime.now()
			reactiveMongoOperations.save(account, "accounts_signout").awaitSingleOrNull()
			accountStatus.deletedAT = LocalDateTime.now()
		}

		accountStatus.status = status
		accountStatusRepository.save(accountStatus) // accountStatus 상태 바꿔줌

		return accountStatus
	}*/
}