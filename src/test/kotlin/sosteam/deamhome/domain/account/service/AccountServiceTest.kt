package sosteam.deamhome.domain.account.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.dto.AccountRequestDTO
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status
import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux


@ExtendWith(SpringExtension::class)
@DataMongoTest
class AccountServiceTest {

    private val logger = LoggerFactory.getLogger(AccountServiceTest::class.java)
    val accountRepository = mockk<AccountRepository>()
    val accountService = AccountService(accountRepository)

    @Test
    @DisplayName("Account 전부 가져오기")
    fun getAllAccounts() {
        val actualAccounts:Flux<Account> = accountService.getAllAccounts()

        actualAccounts
            .doOnNext { account -> println("Account: $account") }
            .blockLast()

    }

    @Test
    @DisplayName("Account 생성 테스트")
    fun createAccount() {


        val requestDTO = AccountRequestDTO(
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
            point = 0
        )
        every { accountRepository.save(any()) } answers { Mono.just(firstArg()) }

        // Test the service method
        val result = accountService.createAccount(
            requestDTO
        ).block()

        logger.info("Result: {}", result)

    }

    @Test
    @DisplayName("Account 상태변환 테스트")
    fun updateAccountStatus() {
    }
}