package sosteam.deamhome.domain.account.repository.custom

import kotlinx.coroutines.flow.Flow
import sosteam.deamhome.domain.account.entity.Account

interface AccountRepositoryCustom {
	fun getMan(): Flow<Account>
}