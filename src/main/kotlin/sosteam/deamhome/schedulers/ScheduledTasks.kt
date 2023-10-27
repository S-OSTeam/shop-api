package sosteam.deamhome.schedulers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.service.AccountGetService
import sosteam.deamhome.domain.account.service.AccountStatusService
import sosteam.deamhome.global.attribute.Status
import java.time.LocalDateTime

@Component
class ScheduledTasks(
    @Autowired
    private val accountGetService: AccountGetService,
    @Autowired
    private val accountStatusService: AccountStatusService,
) {

    @Scheduled(cron = "0 0 2 * * *") // 매일 새벽 2시에 실행
    suspend fun manageDormantMembers()= runBlocking{
        val currentDate = LocalDateTime.now()

        val dormantMembersFlux = accountGetService.getAllAccounts()
            .filter { account -> isDormant(account.lastLoginDateTime, currentDate) }
            .collect { dormantAccount ->
                CoroutineScope(Dispatchers.IO).launch {
                    setMemberToDormant(dormantAccount)
                }
            }
    }

    private fun isDormant(lastLoginDate: LocalDateTime, currentDate: LocalDateTime): Boolean {
        return lastLoginDate != null && lastLoginDate.isBefore(currentDate.minusYears(1))
    }

    suspend fun setMemberToDormant(account: Account){
        accountStatusService.updateAccountStatus(account.userId,Status.DORMANT)

    }


}