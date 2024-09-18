package sosteam.deamhome.domain.account.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.global.attribute.SNS

interface AccountStatusRepositoryCustom {
	fun getStatusByBeforeAYear(): Flow<AccountStatus>
	
	fun getStatusByUserIdOrSNS(
		userId: String?,
		sns: SNS,
		snsId: String?,
		email: String?
	): Flow<AccountStatus>
}