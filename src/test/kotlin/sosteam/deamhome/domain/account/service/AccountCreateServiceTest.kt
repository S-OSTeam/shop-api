package sosteam.deamhome.domain.account.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.exception.AlreadyExistAccountException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.domain.auth.handler.request.AccountCreateRequest
import sosteam.deamhome.global.attribute.SNS
import java.time.LocalDateTime

val accountRepository = mockk<AccountRepository>()
val accountStatusRepository = mockk<AccountStatusRepository>()
val passwordEncoder = mockk<PasswordEncoder>()

@InjectMockKs
val accountCreateService = AccountCreateService(accountRepository, accountStatusRepository, passwordEncoder)

class AccountCreateServiceTest : BehaviorSpec( {
    Given("Trying to join a user") { // 정상 회원 가입
        val userId = "testUser"
        val accountCreateRequest = AccountCreateRequest(
                userId = userId,
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
        val accountStatus = accountCreateRequest.asStatus()
        val account = accountCreateRequest.asDomain()
        coEvery { accountRepository.save(any()) } returns Mono.just(account)

        When("When no problem with all user information") {
            val result = accountCreateService.createAccount(accountCreateRequest)
            Then("Return value must be $userId.") {
                result.userId shouldBe userId
            }
        }

        When("When duplicate members exist") {
            val exception = shouldThrow<AlreadyExistAccountException> {
                accountCreateService.createAccount(accountCreateRequest)
            }
            Then("AlreadyExistAccountException should be made") {
                exception.message shouldBe AlreadyExistAccountException().getMessage()
            }
        }
    }
    /*Given("When user information is empty") {// 유저 정보 누락
        When("When trying to join a user") {
            Then() {} // 애초에 request에서 누락된 정보가 있으면 컴파일 에러
        }
    }*/
})