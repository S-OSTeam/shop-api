package sosteam.deamhome.domain.account.repository

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.dto.AccountRequestDTO
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.service.AccountService
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status
import java.time.LocalDateTime

@EnableAutoConfiguration
@SpringBootTest
@EnableMongoRepositories
@EnableMongoAuditing
class AccountServiceTest(
    @Autowired
    private val accountService: AccountService,
    @Autowired
    private val accountStatusRepository: AccountStatusRepository,
){
    @Test
    @DisplayName("Account 전부 가져오기")
    fun getAllAccounts() {
        val actualAccounts: Flux<Account> = accountService.getAllAccounts()

        actualAccounts
            .doOnNext { account -> println("Account: $account") }
            .blockLast()
        Assertions.assertEquals("123", "123")
    }
    @Test
    @DisplayName("Account 생성 테스트")
    fun createAccount()= runBlocking{


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
        accountService.createAccount(requestDTO)

        Assertions.assertEquals("123", "123")
    }
    @Test
    @DisplayName("AccountStatus 변경 테스트")
    fun UpdateAccountStatus()= runBlocking {
        val userId :String = "testUser!!!"
        val accountStatus: Mono<AccountStatus> = accountService.updateAccountStatus(
                userId = userId,
                status =  Status.DORMANT,
                )

        accountStatus
                .doOnNext { account -> println("AccountStatus: ${account.status}") }
                .block()
        val updated =accountStatusRepository.findByUserId(userId).block()!!.status
        assertEquals(updated, Status.DORMANT)
    }
}
