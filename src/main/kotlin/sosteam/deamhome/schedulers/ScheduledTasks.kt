package sosteam.deamhome.schedulers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.service.AccountService
import sosteam.deamhome.global.attribute.Status
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class ScheduledTasks(
        @Autowired
        private val accountService : AccountService,


) {

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    suspend fun manageDormantMembers()= runBlocking{
        val currentDate = LocalDateTime.now()

        val dormantMembersFlux = accountService.getAllAccounts()
                .filter { account -> isDormant(account.lastLoginDateTime, currentDate) }

        dormantMembersFlux.subscribe { dormantAccount ->
            CoroutineScope(Dispatchers.IO).launch {
                setMemberToDormant(dormantAccount)
            }
        }
    }

    private fun isDormant(lastLoginDate: LocalDateTime, currentDate: LocalDateTime): Boolean {
        return lastLoginDate != null && lastLoginDate.isBefore(currentDate.minusYears(1))
    }

   suspend fun setMemberToDormant(account: Account){
       accountService.updateAccountStatus(account.userId,Status.DORMANT)

   }


}