package sosteam.deamhome.schedulers

import org.springframework.beans.factory.annotation.Autowired
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
    private val accountService : AccountService
) {
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    fun manageDormantMembers() {
        val currentDate = LocalDateTime.now()

        val dormantMembersFlux: Flux<Account> = accountService.getAllAccounts()
            .filter { account -> isDormant(account.lastLoginDateTime, currentDate) }
            .flatMap { account -> setMemberToDormant(account) }

        dormantMembersFlux.subscribe { dormantAccount ->
            println("Account ${dormantAccount.userId} has been set to dormant status.")
        }
    }

    private fun isDormant(lastLoginDate: LocalDateTime, currentDate: LocalDateTime): Boolean {
        return lastLoginDate != null && lastLoginDate.isBefore(currentDate.minusYears(1))
    }

    private fun setMemberToDormant(account: Account): Mono<Account> {
        account.setStatus(Status.DORMANT)
        return accountService.updateAccountStatus(account)
    }

}