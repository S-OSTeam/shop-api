package sosteam.deamhome.domain.account.service

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
import sosteam.deamhome.global.provider.SendMailProvider

@Service
@RequiredArgsConstructor
class AccountSendEmailService(
    private val sendMailProvider: SendMailProvider,
    private val randomStringService: RandomStringService,
    private val accountRepository: AccountRepository,
    private val verifyCodeRedisRepository: VerifyCodeRedisRepository
) {
    suspend fun saveVerifyCodeByType(email: String, type: VerifyType): String {
        val verifyCode = randomStringService.generateKey(6)
        val accountVerifyCode = AccountVerifyCode(email, verifyCode, type, 300)
        withContext(Dispatchers.IO) {
            if(verifyCodeRedisRepository.findById(verifyCode).isPresent) {
                verifyCodeRedisRepository.deleteById(verifyCode)
            }
            verifyCodeRedisRepository.save(accountVerifyCode)
        }

        return verifyCode
    }

    fun generateMessageContent(verifyType: VerifyType, verifyCode: String): Pair<String, String> {
        var subject: String = "" // 메시지 제목
        var body: String = "$verifyCode\n" // 메시지 본문

        // 제목과 본문을 설정
        when (verifyType) {
            VerifyType.SIGNUP -> {
                subject = "deamhome 회원가입 인증 코드"
                body += "회원가입을 위한 인증입니다."
            }
            VerifyType.FIND_USERID -> {
                subject = "deamhome 아이디 찾기 인증 코드"
                body += "아이디 찾기를 위한 인증입니다."
            }
            VerifyType.CHANGE_PWD -> {
                subject = "deamhome 비밀번호 변경 인증 코드"
                body += "아이디 찾기를 위한 인증입니다."
            }
            VerifyType.CHANGE_USER_INFO -> {
                subject = "deamhome 회원 정보 변경 인증 코드"
                body += "아이디 찾기를 위한 인증입니다."
            }
            VerifyType.RESTORE_USER -> {
                subject = "deamhome 계정 복구 인증 코드"
                body += "아이디 찾기를 위한 인증입니다."
            }
        }
        body+="\n본인이 맞다면 5분 이내에 인증 코드를 입력해 주세요"

        return Pair(subject, body)
    }

    suspend fun sendVerifyCode(email: String, verifyType: VerifyType): String {
        val user = accountRepository.findAccountByUserId(email)
            ?: throw AccountNotFoundException()

        val verifyCode = saveVerifyCodeByType(user.email, verifyType)
        val message = generateMessageContent(verifyType, verifyCode)

        sendMailProvider.sendEmail(email, message.first, message.second)
        return verifyCode
    }

    suspend fun checkCodeByType(email: String, code: String, verifyType: VerifyType): String {
        val foundCode = withContext(Dispatchers.IO) {
            verifyCodeRedisRepository.findById(code)
        }
        if (foundCode.isEmpty || foundCode.get().email != email || foundCode.get().type != verifyType) {
            throw VerifyCodeNotFoundException()
        }
        withContext(Dispatchers.IO) {
            verifyCodeRedisRepository.deleteById(code)
        }
        return email
    }
}