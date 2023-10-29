package sosteam.deamhome.domain.account.repository.impl

import lombok.RequiredArgsConstructor
import sosteam.deamhome.domain.account.entity.QAccount
import sosteam.deamhome.domain.account.repository.custom.AccountCustomRepository
import sosteam.deamhome.domain.account.repository.querydsl.AccountQueryDslRepository

@RequiredArgsConstructor
class AccountCustomRepositoryImpl(
	private val repository: AccountQueryDslRepository,
) : AccountCustomRepository {
	private val account = QAccount.account
	
}