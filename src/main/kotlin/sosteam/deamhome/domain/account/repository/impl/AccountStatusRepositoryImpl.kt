package sosteam.deamhome.domain.account.repository.impl

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.BooleanExpression
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
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
	
	override fun getStatusByUserIdOrSNS(
		userId: String?,
		sns: SNS,
		snsId: String?,
		email: String?
	): Flow<AccountStatus> {
		return querydsl.findAll(
			eqLoginData(sns, snsId, userId, email)
		).asFlow()
	}
	
	//로그인을 id로 하는지 user로 하는지 sns로 하는지 확인
	fun eqLoginData(sns: SNS, snsId: String?, userId: String?, email: String?): BooleanBuilder {
		val expr = BooleanBuilder()
		
		//SNS가 이상한 경우는 request에서 걸러진다.
		//id와 email이 이상한 경우는 걸러지지 않으므로 비어있는지 따로 확인한다.
		val exprId = BooleanBuilder()
		if (!userId.isNullOrBlank()) {
			exprId.or(eqUserId(userId))
		}
		if (!email.isNullOrBlank()) {
			exprId.or(eqEmail(email))
		}
		if (sns != SNS.NORMAL) {
			exprId.or(eqSNS(snsId))
		}
		expr.and(exprId)
		
		return expr
	}
	
	private fun eqSNS(snsId: String?): BooleanExpression? {
		if (snsId.isNullOrBlank()) {
			return null
		}
		return accountStatus.snsId.eq(snsId)
	}
	
	private fun eqEmail(email: String?): BooleanExpression? {
		if (email.isNullOrBlank()) {
			return null
		}
		return accountStatus.email.eq(email)
	}
	
	private fun eqUserId(userId: String?): BooleanExpression? {
		if (userId.isNullOrBlank()) {
			return null
		}
		return accountStatus.userId.eq(userId)
	}
}