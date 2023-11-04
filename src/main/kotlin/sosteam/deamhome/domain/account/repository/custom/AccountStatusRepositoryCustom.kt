package sosteam.deamhome.domain.account.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.account.entity.AccountStatus

interface AccountStatusRepositoryCustom {
	fun findStatusByBeforeAYear(): Flow<AccountStatus>
}