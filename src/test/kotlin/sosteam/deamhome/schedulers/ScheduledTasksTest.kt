package sosteam.deamhome.schedulers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.service.AccountService
import java.time.LocalDateTime
import io.mockk.every
import io.mockk.mockk
import reactor.core.publisher.Flux

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ScheduledTasksTest {

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
}