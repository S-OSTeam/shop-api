package sosteam.deamhome.domain.account.service

import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.handler.response.AccountResponse
import sosteam.deamhome.domain.account.repository.AccountRepository

@Service
class AccountReadService(
    private val accountValidService: AccountValidService
){
    suspend fun getAccount(userId: String): AccountResponse {
        val account: Account = accountValidService.getAccountByUserId(userId)
        return AccountResponse.fromAccount(account)
    }
}