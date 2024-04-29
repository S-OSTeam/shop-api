package sosteam.deamhome.domain.account.repository

import lombok.RequiredArgsConstructor
import sosteam.deamhome.domain.account.service.AccountCreateService
import sosteam.deamhome.global.test.RepositoryBaseTest


@RequiredArgsConstructor
class AccountServiceTest(
	private val accountCreateService: AccountCreateService,
	private val accountStatusRepository: AccountStatusRepository,
) : RepositoryBaseTest() {
	
	/*@Test
	@DisplayName("Account 생성 테스트")
	fun createAccount() = runBlocking {
		val userId = "testUser"

		val requestDTO = AccountCreateRequest(
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
		accountCreateService.createAccount(requestDTO)
		val updated = accountStatusRepository.findByUserId(userId)?.userId
		assertEquals(updated, userId)

	}*/
}
