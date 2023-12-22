package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.global.RepositoryBaseTest
import sosteam.deamhome.global.attribute.Role
import sosteam.deamhome.global.attribute.SNS
import java.time.LocalDateTime


class AccountRepositoryTest @Autowired constructor(
	private val accountRepository: AccountRepository,
) : RepositoryBaseTest() {
	
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
			role = Role.ROLE_GUEST,
			loginAt = LocalDateTime.now()
		)
		
		
		accountRepository.insert(account).awaitSingle()
		
		assertEquals("123", "123")
		
	}
	/*
<<<<<<< Updated upstream
	@Test
	@DisplayName("userId로 찾기 테스트")
	fun FindByUserIdTest() = runBlocking {
		val userId = "testUser"
		val account: Account = accountRepository.findAccountByUserId(userId)
		log().info("test")
		
		assertEquals("123", "123")
	}

=======
>>>>>>> Stashed changes*/
}