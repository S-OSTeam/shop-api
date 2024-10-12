package sosteam.deamhome.domain.auth.handler

import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.account.service.AccountSendEmailService
import sosteam.deamhome.domain.auth.handler.request.CheckVerifyCodeRequest
import sosteam.deamhome.domain.auth.handler.request.SendVerifyCodeRequest
import sosteam.deamhome.global.provider.RequestProvider.Companion.getIP

@RestController
@RequiredArgsConstructor
class AccountAuthMailResolver(
	val accountSendEmailService: AccountSendEmailService
) {
	@MutationMapping
	suspend fun sendVerifyCode(
		@Argument @Valid request: SendVerifyCodeRequest
	): String {
		val ip = getIP()
		return accountSendEmailService.sendVerifyCode(request.email, request.verifyType)
	}
	
	@MutationMapping
	suspend fun checkVerifyCodeBy(
		@Argument @Valid request: CheckVerifyCodeRequest
	): String {
		val ip = getIP()
		return accountSendEmailService.checkCodeByType(request.email, request.verifyCode, request.verifyType)
	}
}