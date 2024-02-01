package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.AccountVerifyCode
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.exception.VerifyCodeNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.VerifyCodeRedisRepository
import sosteam.deamhome.global.attribute.VerifyType
import sosteam.deamhome.global.mail.RandomStringService
import sosteam.deamhome.global.mail.SendMailService

@Service
@RequiredArgsConstructor
class AccountSendEmailService(
    private val sendMailService: SendMailService,
    private val randomStringService: RandomStringService,
    private val accountRepository: AccountRepository,
    private val verifyCodeRedisRepository: VerifyCodeRedisRepository
) {
    suspend fun saveVerifyCodeByType(email: String, type: VerifyType): String {
        val verifyCode = randomStringService.generateKey(6)
        val accountVerifyCode = AccountVerifyCode(email+verifyCode, type, 300)
        withContext(Dispatchers.IO) {
            if(verifyCodeRedisRepository.findById(email).isPresent) {
                verifyCodeRedisRepository.deleteById(email)
            }
            verifyCodeRedisRepository.save(accountVerifyCode)
        }

        return verifyCode
    }

    suspend fun sendSignupVerifyCode(email: String): String {
        val verifyCode = saveVerifyCodeByType(email, VerifyType.SIGNUP)
        sendMailService.sendTestEmail(email, "deamhome 이메일 인증 코드", "$verifyCode\n" +
                "deamhome 회원가입 인증 코드입니다.\n" +
                "5분 이내에 인증 코드를 입력해 주세요") // 프론트 이메일 인증 변경 링크
        return verifyCode
    }

    suspend fun sendChangePwdVerifyCode(userId: String): String {
        val user = accountRepository.findAccountByUserId(userId)
            ?: throw AccountNotFoundException()

        val verifyCode = saveVerifyCodeByType(user.email, VerifyType.CHANGEPWD)
        sendMailService.sendTestEmail(user.email, "deamhome 비밀번호 변경 코드", "$verifyCode\n" +
                "deamhome 비밀번호 번경 인증 코드입니다.\n" +
                "본인이 맞다면 5분 이내에 인증 코드를 입력해 주세요") // 프론트 바말번호 변경 링크
        return verifyCode
    }

    suspend fun checkCodeByType(code: String, email: String, verifyType: VerifyType): String {
        val foundCode = withContext(Dispatchers.IO) {
            verifyCodeRedisRepository.findById(email+code)
        }
        if (foundCode.isEmpty) {
            throw VerifyCodeNotFoundException()
        }
        verifyCodeRedisRepository.deleteById(email+code)
        return email
    }


    suspend fun checkChangePwdVerifyCode(userId: String, code: String): String {
        val user = accountRepository.findAccountByUserId(userId)
            ?: throw AccountNotFoundException()
        return checkCodeByType(code, user.email, VerifyType.CHANGEPWD)
    }
}