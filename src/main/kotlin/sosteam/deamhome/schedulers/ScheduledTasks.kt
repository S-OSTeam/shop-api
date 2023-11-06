package sosteam.deamhome.schedulers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lombok.RequiredArgsConstructor
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.service.AccountStatusModifyService
import sosteam.deamhome.global.attribute.Status
import java.time.LocalDateTime

@RequiredArgsConstructor
@Component
class ScheduledTasks(
	private val accountRepository: AccountRepository,
	private val accountStatusModifyService: AccountStatusModifyService,
) {
<<<<<<< Updated upstream

    @Scheduled(cron = "0 0 2 * * *") // 매일 새벽 2시에 실행
    suspend fun manageDormantMembers(){
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


=======
	
	@Async
	@Scheduled(cron = "0 0 2 * * *") // 매일 새벽 2시에 실행
	fun manageDormantMembers() = runBlocking {
		val currentDate = LocalDateTime.now()
		
		val dormantMembersFlux = accountRepository.getDormantAccount()
			.collect { dormantAccount ->
				CoroutineScope(Dispatchers.IO).launch {
					setMemberToDormant(dormantAccount)
				}
			}
	}
	
	suspend fun setMemberToDormant(account: Account) {
		accountStatusModifyService.updateAccountStatus(account.userId, Status.DORMANT)
	}
>>>>>>> Stashed changes
}