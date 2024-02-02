package sosteam.deamhome.schedulers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lombok.RequiredArgsConstructor
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.domain.account.service.AccountStatusModifyService
import sosteam.deamhome.global.attribute.Status
import sosteam.deamhome.global.mail.SendMailService
import java.time.LocalDateTime
import java.time.Period

@RequiredArgsConstructor
@Component
class ScheduledTasks(
	private val accountRepository: AccountRepository,
	private val accountStatusRepository: AccountStatusRepository,
	private val sendMailService: SendMailService
) {
	@Async
	@Scheduled(cron = "0 0 2 * * *") // 매일 새벽 2시에 실행
	fun manageDormantMembers() = runBlocking {
		val currentDate = LocalDateTime.now()

		// TODO 삭제 된 회원 1주일 후 삭제 예정 메일 2주일 후 삭제 메일 및 삭제
		val signoutMembersFlux = accountStatusRepository.findAllByStatus(Status.SIGNOUT)
			.collect { signoutAccountStatus ->
				CoroutineScope(Dispatchers.IO).launch {
					sendDeleteMail(signoutAccountStatus, currentDate)
				}
			}
	}

	suspend fun sendDeleteMail(signoutAccountStatus: AccountStatus, currentTime: LocalDateTime) {
		val signoutAccount = accountRepository.findAccountByUserId(signoutAccountStatus.userId)
		val duration = Period.between(signoutAccountStatus.updateTime.toLocalDate(), currentTime.toLocalDate())
		if (duration.days == 7) {
			signoutAccount?.let {
				runBlocking {
					sendMailService.sendTestEmail(it.email, "deamhome 계정 삭제 예정 메일", "귀하의 계정이 1주일 후에 삭제 될 예정입니다.")
				}
			}
		} else if (duration.days == 14) {
			signoutAccount?.let {
				runBlocking {
					sendMailService.sendTestEmail(it.email, "deamhome 계정 삭제 메일", "귀하의 계정이 삭제 되었습니다.")
				}
			}
		}
	}
}