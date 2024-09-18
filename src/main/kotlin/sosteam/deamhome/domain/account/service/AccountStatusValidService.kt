package sosteam.deamhome.domain.account.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.account.entity.AccountStatus
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.exception.AccountNotLiveException
import sosteam.deamhome.domain.account.exception.AlreadyExistAccountException
import sosteam.deamhome.domain.account.exception.UserIdOrEmailDuplicateException
import sosteam.deamhome.domain.account.repository.AccountStatusRepository
import sosteam.deamhome.domain.kakao.service.KakaoService
import sosteam.deamhome.domain.naver.service.NaverService
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Status
import sosteam.deamhome.global.provider.log

@Service
@Transactional
@RequiredArgsConstructor
class AccountStatusValidService(
	private val accountStatusRepository: AccountStatusRepository,
	private val naverService: NaverService,
	private val kakaoService: KakaoService
) {
	suspend fun getStatusByUserIdOrSNS(userId: String?, sns: SNS, snsId: String?, email: String?): Flow<AccountStatus> {
		return accountStatusRepository.getStatusByUserIdOrSNS(userId, sns, snsId, email)
	}
	
	suspend fun getSnsId(userId: String?, sns: SNS, snsToken: String?): String? {
		return when (sns) {
			SNS.NORMAL -> userId
			SNS.NAVER -> snsToken?.let { naverService.getNaverUserId(it) }
			SNS.KAKAO -> snsToken?.let { kakaoService.getKakaoUserId(it) }
			else -> null
		}
	}
	
	suspend fun getSnsToken(sns: SNS, snsCode: String?): String? {
		return when (sns) {
			SNS.NAVER -> snsCode?.let { naverService.getNaverToken(it) }
			SNS.KAKAO -> snsCode?.let { kakaoService.getKakaoToken(it) }
			else -> null
		}
	}
	
	suspend fun getLiveAccountIdByStatus(userId: String?, sns: SNS, snsToken: String?, email: String?): Long {
		val snsId = getSnsId(userId, sns, snsToken)
		val accountStatusList = getStatusByUserIdOrSNS(userId, sns, snsId, email).toList()
		
		log().debug(accountStatusList.size.toString())
		if (accountStatusList.isEmpty())
			throw AccountNotFoundException()
		else if (accountStatusList.size > 1)
			throw UserIdOrEmailDuplicateException()
		
		val accountStatus = accountStatusList[0]
		if (accountStatus.status != Status.LIVE)
			throw AccountNotLiveException()
		
		return accountStatus.accountId!!
	}
	
	suspend fun isNotExistAccount(
		userId: String?,
		sns: SNS,
		snsToken: String?,
		email: String?
	): Boolean {
		val snsId = getSnsId(userId, sns, snsToken)
		val accountStatusList = getStatusByUserIdOrSNS(userId, sns, snsId, email).toList()
		
		if (accountStatusList.size == 1)
			throw AlreadyExistAccountException()
		
		if (accountStatusList.size > 1)
			throw UserIdOrEmailDuplicateException()
		
		return true
	}
}
