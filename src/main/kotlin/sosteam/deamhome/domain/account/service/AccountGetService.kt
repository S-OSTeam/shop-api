package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository

@Service
class AccountGetService(
    private val accountRepository: AccountRepository,
){
    fun getAllAccounts(): Flow<Account> {
        return accountRepository.findAll().asFlow()
    }
    fun findByUserId(userId:String): Mono<Account> {
        return accountRepository.findByUserId(userId)
    }

}