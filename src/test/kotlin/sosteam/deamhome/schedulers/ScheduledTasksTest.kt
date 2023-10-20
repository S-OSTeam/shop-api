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
import kotlinx.coroutines.reactive.awaitFirstOrDefault
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountRepositoryTest
import sosteam.deamhome.global.attribute.Status


@EnableAutoConfiguration
@SpringBootTest
@EnableMongoRepositories
@EnableMongoAuditing
class ScheduledTasksTest (
        @Autowired
        private val accountService : AccountService,
        @Autowired
        private val reactiveMongoOperations: ReactiveMongoOperations,
        @Autowired
        private val accountRepository: AccountRepository,

){



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
    fun SetMemberToDormant()= runBlocking {
        val userId :String = "testUser"
        val account:Account? = accountRepository.findByUserId(userId).awaitFirstOrDefault(null)

        if(account == null){
            //에러처리
        }else{
            println("id : ${account!!.userId}")
            accountService.updateAccountStatus(account.userId,Status.DORMANT)
            reactiveMongoOperations.save(account, "accounts_dormant").awaitSingleOrNull()
            accountService.deleteAccount(account)
        }
    }
}