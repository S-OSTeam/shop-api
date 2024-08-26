package sosteam.deamhome.domain.account.service

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.exception.AccountNotLiveException
import sosteam.deamhome.domain.account.exception.AlreadyExistAccountException
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.domain.kakao.service.KakaoService
import sosteam.deamhome.domain.naver.service.NaverService
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status

@Service
@Transactional
@RequiredArgsConstructor
class AccountStatusValidService(
	private val accountStatusRepository: AccountStatusRepository,
	private val naverService: NaverService,
	private val kakaoService: KakaoService
) {
	suspend fun getStatusByUserIdOrSNS(userId: String?, sns: SNS, snsId: String?, email: String?): AccountStatus? {
		return accountStatusRepository.getStatusByUserIdOrSNS(userId, sns, snsId, email)
	}
	
	suspend fun getSnsId(userId: String?, sns: SNS, snsCode: String?): String? {
		return when (sns) {
			SNS.NORMAL -> userId
			SNS.NAVER -> snsCode?.let { naverService.getNaverUserId(it) }
			SNS.KAKAO -> snsCode?.let { kakaoService.getKakaoUserId(it) }
			else -> null
		}
	}
	
	suspend fun getLiveAccountIdByStatus(userId: String?, sns: SNS, snsCode: String?, email: String?): Long {
		val snsId = getSnsId(userId, sns, snsCode)
		
		val accountStatus = getStatusByUserIdOrSNS(userId, sns, snsId, email) ?: throw AccountNotFoundException()
		
		if (accountStatus.status != Status.LIVE)
			throw AccountNotLiveException()
		
		return accountStatus.accountId!!
	}
	
	suspend fun isNotExistAccount(
		userId: String?,
		sns: SNS,
		snsCode: String?,
		email: String?
	): Boolean {
		val snsId = getSnsId(userId, sns, snsCode)
		val accountStatus = getStatusByUserIdOrSNS(userId, sns, snsId, email)
		
		if (accountStatus != null)
			throw AlreadyExistAccountException()
		
		return true
	}
}