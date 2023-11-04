package sosteam.deamhome.domain.account.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.QAccount
import sosteam.deamhome.domain.account.repository.custom.AccountRepositoryCustom
import sosteam.deamhome.domain.account.repository.querydsl.AccountQueryDslRepository

@RequiredArgsConstructor
@GraphQlRepository
class AccountRepositoryImpl(
	private val querydsl: AccountQueryDslRepository,
) : AccountRepositoryCustom {
	private val account = QAccount.account
	override fun getMan(): Flow<Account> {
		return querydsl.findAll(account.sex.eq(true)).asFlow()
	}
	
}