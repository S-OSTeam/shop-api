package sosteam.deamhome.domain.auth.handler

import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.account.service.AccountSendEmailService
import sosteam.deamhome.domain.auth.handler.request.*
import sosteam.deamhome.global.attribute.VerifyType
import sosteam.deamhome.global.provider.RequestProvider.Companion.getMac

@RestController
@RequiredArgsConstructor
class AccountAuthMailResolver(
    val accountSendEmailService: AccountSendEmailService
) {
    @MutationMapping
    suspend fun sendEmailSignupCode(
        request: SignupVerifyCodeRequest
    ): String {
        val mac = getMac()
        return accountSendEmailService.sendSignupVerifyCode(request.email)
    }

    @MutationMapping
    suspend fun sendEmailChangePwdCode(
        request: ChangePwdSendVerifyCodeRequest
    ): String {
        val mac = getMac()
        return accountSendEmailService.sendChangePwdVerifyCode(request.userId)
    }

    @MutationMapping
    suspend fun checkSignupVerifyCode(
        request: CheckSignupVerifyCodeRequest
    ): String {
        val mac = getMac()
        return accountSendEmailService.checkCodeByType(request.verifyCode, request.email, VerifyType.SIGNUP)
    }

    @MutationMapping
    suspend fun checkChangePwdVerifyCode(
        request: CheckChangePwdVerifyCodeRequest
    ): String {
        val mac = getMac()
        return accountSendEmailService.checkChangePwdVerifyCode(request.userId, request.verifyCode)
    }

    @MutationMapping
    suspend fun checkGetUserIdVerifyCode(
        request: CheckGetUserIdVerifyCodeRequest
    ): String {
        val mac = getMac()
        return accountSendEmailService.checkGetUserIdVerifyCode(request.email, request.verifyCode)
    }

    @MutationMapping
    suspend fun checkChangeUserInfoVerifyCode(
        request: CheckChangeUserInfoVerifyCodeRequest
    ): String {
        val mac = getMac()
        return accountSendEmailService.checkChangeUserInfoVerifyCode(request.userId, request.verifyCode)
    }

    @MutationMapping
    suspend fun checkRestoreUser(
        request: CheckRestoreUserRequest
    ): String {
        val mac = getMac()
        return accountSendEmailService.checkRestoreUser(request.userId, request.verifyCode)
    }
}