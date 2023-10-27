package sosteam.deamhome.domain.account.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository

@Service
class AccountGetService(
    private val accountRepository: AccountRepository,
){
    fun getAllAccounts(): Flux<Account> {
        return accountRepository.findAll()
    }
    fun findByUserId(userId:String): Mono<Account> {
        return accountRepository.findByUserId(userId)
    }

}