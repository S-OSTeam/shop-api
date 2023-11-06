package sosteam.deamhome.domain.account.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import lombok.RequiredArgsConstructor
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.entity.QAccountStatus
import sosteam.deamhome.domain.account.repository.custom.AccountStatusRepositoryCustom
import sosteam.deamhome.domain.account.repository.querydsl.AccountStatusQueryDslRepository
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status

@RequiredArgsConstructor
class AccountStatusRepositoryImpl(
	private val querydsl: AccountStatusQueryDslRepository,
) : AccountStatusRepositoryCustom {
	private val accountStatus = QAccountStatus.accountStatus
	
	override fun getStatusByBeforeAYear(): Flow<AccountStatus> {
		return querydsl.findAll(accountStatus.status.eq(Status.LIVE)).asFlow()
	}
	
	override suspend fun getStatusByUserIdOrSNS(userId: String, sns: SNS, snsId: String): AccountStatus? {
		return querydsl.findOne(
			accountStatus.userId.eq(userId).or(accountStatus.sns.eq(sns).and(accountStatus.snsId.eq(snsId)))
		).awaitSingle()
	}
}