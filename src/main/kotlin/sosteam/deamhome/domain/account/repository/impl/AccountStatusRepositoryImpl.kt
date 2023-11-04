package sosteam.deamhome.domain.account.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.GraphQlRepository
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.entity.QAccountStatus
import sosteam.deamhome.domain.account.repository.custom.AccountStatusRepositoryCustom
import sosteam.deamhome.domain.account.repository.querydsl.AccountStatusQueryDslRepository
import sosteam.deamhome.global.attribute.Status

@RequiredArgsConstructor
@GraphQlRepository
class AccountStatusRepositoryImpl(
	private val querydsl: AccountStatusQueryDslRepository,
) : AccountStatusRepositoryCustom {
	private val accountStatus = QAccountStatus.accountStatus
	
	override fun findStatusByBeforeAYear(): Flow<AccountStatus> {
		return querydsl.findAll(accountStatus.status.eq(Status.LIVE)).asFlow()
	}
}