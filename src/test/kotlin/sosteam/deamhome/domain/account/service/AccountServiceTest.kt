package sosteam.deamhome.domain.account.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import sosteam.deamhome.domain.account.dto.request.AccountRequestDTO
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.service.AccountDataControlService
import sosteam.deamhome.domain.account.service.AccountGetService
import sosteam.deamhome.global.RepositoryBaseTest
import sosteam.deamhome.global.attribute.SNS
import java.time.LocalDateTime


class AccountServiceTest(
	@Autowired
	private val accountDataControlService: AccountDataControlService,
	@Autowired
	private val accountStatusRepository: AccountStatusRepository,
	@Autowired
	private val accountGetService: AccountGetService,
) : RepositoryBaseTest() {
	@Test
	@DisplayName("Account 전부 가져오기")
	fun getAllAccounts() = runBlocking {
		val actualAccounts: Flow<Account> = accountGetService.getAllAccounts()
		
		actualAccounts.collect { account ->
			println("Account: $account")
		}
		assertEquals("123", "123")
	}
	
	@Test
	@DisplayName("Account 생성 테스트")
	fun createAccount() = runBlocking {
		val userId = "testUser"
		
		val requestDTO = AccountRequestDTO(
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
		accountDataControlService.createAccount(requestDTO)
		val updated = accountStatusRepository.findByUserId(userId).block()!!.userId
		assertEquals(updated, userId)
		
	}
}
