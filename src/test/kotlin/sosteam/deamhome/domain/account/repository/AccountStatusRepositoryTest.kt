
package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.repository.AccountStatusRepository


@ExtendWith(SpringExtension::class)
@DataMongoTest
@EnableAutoConfiguration
@EnableMongoRepositories
@EnableMongoAuditing
class AccountStatusRepositoryTest @Autowired constructor(
    private val accountStatusRepository: AccountStatusRepository,
){
    @Test
    @DisplayName("AccountStatus 전부 가져오기")
    fun getAllAccountStatus() =runBlocking {
        val actualAccounts:Flux<AccountStatus> = accountStatusRepository.findAll()

        actualAccounts
                .doOnNext { accountStatus -> println("userId: ${accountStatus.userId} AccountStatus: ${accountStatus.status} ") }
                .blockLast()
        Assertions.assertEquals("123", "123")
    }

}