
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
import reactor.core.publisher.Mono
import sosteam.deamhome.global.BaseTest


class AccountRepositoryTest @Autowired constructor(
    private val accountRepository:AccountRepository,
):BaseTest()
{
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Test
    @DisplayName("Account 전부 가져오기")
    fun getAllAccounts() =runBlocking {
        val actualAccounts:Flux<Account> = accountRepository.findAll()

        actualAccounts
            .doOnNext { account -> logger.info("Account: $account") }
            .blockLast()
        Assertions.assertEquals("123", "123")
    }

    @Test
    @DisplayName("Account 생성 테스트")
    fun createAccount() = runBlocking {
        val account = Account(
            userId = "testUser",
            pwd = "password",
            sex = true,
            birtyday = LocalDateTime.now(),
            zipcode = "12345",
            address1 = "123 Main St",
            address2 = "Apt 101",
            address3 = "City",
            address4 = "State",
            email = "test@example.com",
            receiveMail = true,
            createdIp = "127.0.0.1",
            snsId = "testSnsId",
            sns = SNS.NORMAL,
            phone = "1234567890",
            userName = "Test User",
            point = 0,
            role=Role.ROLE_GUEST,
            lastLoginDateTime = LocalDateTime.now()
        )


        accountRepository.insert(account).awaitSingle()

        Assertions.assertEquals("123", "123")

    }

    @Test
    @DisplayName("userId로 찾기 테스트")
    fun FindByUserIdTest()= runBlocking{
        val userId = "testUser"
        val account: Account? = accountRepository.findByUserId(userId)
        println("id : ${account?.userId}")
        Assertions.assertEquals("123", "123")
    }

}