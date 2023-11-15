package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Flux
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.global.RepositoryBaseTest


class AccountStatusRepositoryTest @Autowired constructor(
    private val accountStatusRepository: AccountStatusRepository,
) : RepositoryBaseTest() {
    @Test
    @DisplayName("AccountStatus 전부 가져오기")
    fun getAllAccountStatus() = runBlocking {
        val actualAccounts: Flux<AccountStatus> = accountStatusRepository.findAll()

        actualAccounts
            .doOnNext { accountStatus -> println("userId: ${accountStatus.userId} AccountStatus: ${accountStatus.status} ") }
            .blockLast()
        Assertions.assertEquals("123", "123")
    }

}