//package sosteam.deamhome.domain.account.repository
//
//import kotlinx.coroutines.runBlocking
//import lombok.RequiredArgsConstructor
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Test
//import sosteam.deamhome.domain.account.service.AccountCreateService
//import sosteam.deamhome.domain.auth.handler.request.AccountCreateRequest
//import sosteam.deamhome.global.RepositoryBaseTest
//import sosteam.deamhome.global.attribute.SNS
//import java.time.LocalDateTime
//
//
//@RequiredArgsConstructor
//class AccountServiceTest(
//	private val accountCreateService: AccountCreateService,
//	private val accountStatusRepository: AccountStatusRepository,
//) : RepositoryBaseTest() {
//
//	/*@Test
//	@DisplayName("Account 생성 테스트")
//	fun createAccount() = runBlocking {
//		val userId = "testUser"
//
//		val requestDTO = AccountCreateRequest(
//			userId = userId,
//			pwd = "password",
//			sex = true,
//			birtyday = LocalDateTime.now(),
//			zipcode = "12345",
//			address1 = "123 Main St",
//			address2 = "Apt 101",
//			address3 = "City",
//			address4 = "State",
//			email = "test@example.com",
//			receiveMail = true,
//			createdIp = "127.0.0.1",
//			snsId = "testSnsId",
//			sns = SNS.NORMAL,
//			phone = "1234567890",
//			userName = "Test User",
//			point = 0
//
//		)
//		accountCreateService.createAccount(requestDTO)
//		val updated = accountStatusRepository.findByUserId(userId)?.userId
//		assertEquals(updated, userId)
//
//	}*/
//}
