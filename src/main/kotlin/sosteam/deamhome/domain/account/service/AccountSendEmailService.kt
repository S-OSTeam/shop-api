package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import sosteam.deamhome.domain.account.entity.AccountVerifyCode
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.exception.AlreadyExistAccountException
import sosteam.deamhome.domain.account.exception.VerifyCodeNotFoundException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.account.repository.VerifyCodeRedisRepository
import sosteam.deamhome.global.attribute.VerifyType
import sosteam.deamhome.global.provider.RandomStringProvider
import sosteam.deamhome.global.provider.SendMailProvider

@Service
@RequiredArgsConstructor
class AccountSendEmailService(
	private val sendMailProvider: SendMailProvider,
	private val randomStringService: RandomStringProvider,
	private val accountRepository: AccountRepository,
	private val verifyCodeRedisRepository: VerifyCodeRedisRepository
) {
	suspend fun saveVerifyCodeByType(email: String, type: VerifyType): String {
		val verifyCode = randomStringService.generateKey(6)
		val accountVerifyCode = AccountVerifyCode(email, verifyCode, type, 300)
		withContext(Dispatchers.IO) {
			if (verifyCodeRedisRepository.findById(verifyCode).isPresent) {
				verifyCodeRedisRepository.deleteById(verifyCode)
			}
			verifyCodeRedisRepository.save(accountVerifyCode)
		}
		
		return verifyCode
	}
	
	suspend fun sendVerifyCode(email: String, verifyType: VerifyType): String {
		val user = accountRepository.findAccountByUserId(email)
		if (verifyType != VerifyType.SIGNUP && user == null)
			throw AccountNotFoundException()
		if (verifyType == VerifyType.SIGNUP && user != null)
			throw AlreadyExistAccountException()
		
		val verifyCode = saveVerifyCodeByType(email, verifyType)
		
		var subject: String = verifyType.subject // 메시지 제목
		var body: String = "$verifyCode\n" + verifyType.body + "\n본인이 맞다면 5분 이내에 인증 코드를 입력해 주세요" // 메시지 본문
		//var body: String = "$verifyCode\n${verifyType.body}\n본인이 맞다면 5분 이내에 인증 코드를 입력해 주세요"
		
		
		sendMailProvider.sendEmail(email, subject, body)
		return email
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