package sosteam.deamhome.domain.auth.handler

import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.account.service.AccountSendEmailService
import sosteam.deamhome.domain.auth.handler.request.*
import sosteam.deamhome.global.provider.RequestProvider.Companion.getMac

@RestController
@RequiredArgsConstructor
class AccountAuthMailResolver(
    val accountSendEmailService: AccountSendEmailService
) {
    @MutationMapping
    suspend fun sendVerifyCode(
        request: SendVerifyCodeRequest
    ): String {
        val mac = getMac()
        return accountSendEmailService.sendVerifyCode(request.email, request.verifyType)
    }

    @MutationMapping
    suspend fun checkVerifyCodeBy(
        request: CheckVerifyCodeRequest
    ): String {
        val mac = getMac()
        return accountSendEmailService.checkCodeByType(request.email, request.verifyCode, request.verifyType)
    }
}