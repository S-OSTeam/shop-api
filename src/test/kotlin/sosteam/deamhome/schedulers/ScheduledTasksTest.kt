package sosteam.deamhome.schedulers

import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountDataControlService
import sosteam.deamhome.domain.account.service.AccountStatusService
import sosteam.deamhome.global.RepositoryBaseTest
import sosteam.deamhome.global.attribute.Status


class ScheduledTasksTest(
	@Autowired
	private val accountStatusService: AccountStatusService,
	@Autowired
	private val reactiveMongoOperations: ReactiveMongoOperations,
	@Autowired
	private val accountRepository: AccountRepository,
	@Autowired
	private val accountDataControlService: AccountDataControlService,
	
	) : RepositoryBaseTest() {
	
	
	//    @Test
//    fun `test manageDormantMembers`() {
//        // Mock AccountService
//        val accountService = mockk<AccountService>()
//        every { accountService.getAllAccounts() } returns Flux.just(
//            Account(userId = "user1", lastLoginDateTime = LocalDateTime.now().minusYears(2)),
//            Account(userId = "user2", lastLoginDateTime = LocalDateTime.now().minusMonths(6))
//        )
//
//        val scheduledTasks = ScheduledTasks(accountService)
//
//        // Run the task
//        scheduledTasks.manageDormantMembers()
//    }
	@Test
	@DisplayName("Dormant 전환함수 테스트")
	fun SetMemberToDormant() = runBlocking {
		val userId: String = "testUser"
		val account: Account = accountRepository.findAccountByUserId(userId)
		
		if (account == null) {
			//TODO: 에러처리
		} else {
			println("id : ${account.userId}")
			accountStatusService.updateAccountStatus(account.userId, Status.DORMANT)
			reactiveMongoOperations.save(account, "accounts_dormant").awaitSingleOrNull()
			accountDataControlService.deleteAccount(account)
		}
	}
}