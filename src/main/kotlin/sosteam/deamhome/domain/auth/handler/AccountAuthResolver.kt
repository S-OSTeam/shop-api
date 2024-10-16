package sosteam.deamhome.domain.auth.handler

import graphql.GraphQLContext
import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.account.service.AccountCreateService
import sosteam.deamhome.domain.account.service.AccountModifyService
import sosteam.deamhome.domain.account.service.AccountStatusValidService
import sosteam.deamhome.domain.account.service.AccountValidService
import sosteam.deamhome.domain.auth.handler.request.AccountChangePwdRequest
import sosteam.deamhome.domain.auth.handler.request.AccountCreateRequest
import sosteam.deamhome.domain.auth.handler.request.AccountLoginRequest
import sosteam.deamhome.domain.auth.handler.request.CheckDuplicateUserRequest
import sosteam.deamhome.domain.auth.service.AccountAuthCreateService
import sosteam.deamhome.domain.auth.service.AccountAuthDeleteService
import sosteam.deamhome.global.provider.RequestProvider.Companion.getAgent
import sosteam.deamhome.global.provider.RequestProvider.Companion.getIP
import sosteam.deamhome.global.provider.RequestProvider.Companion.getRefreshToken
import sosteam.deamhome.global.provider.RequestProvider.Companion.getSNSToken
import sosteam.deamhome.global.provider.RequestProvider.Companion.getToken
import sosteam.deamhome.global.provider.log
import sosteam.deamhome.global.security.response.TokenResponse

@RestController
class AccountAuthResolver(
	val accountAuthCreateService: AccountAuthCreateService,
	val accountAuthDeleteService: AccountAuthDeleteService,
	val accountCreateService: AccountCreateService,
	val accountValidService: AccountValidService,
	val accountStatusValidService: AccountStatusValidService,
	val accountChangePwdService: AccountModifyService
) {
	@MutationMapping
	suspend fun signUp(@Argument @Valid request: AccountCreateRequest): String {
		val ip = getIP()
		
		val snsToken = getSNSToken()
		
		val createAccount = accountCreateService.createAccount(request, snsToken)
		return createAccount.userId
	}
	
	@MutationMapping
	suspend fun checkDuplicateUser(@Argument request: CheckDuplicateUserRequest): Boolean {
		try {
			val snsToken = getSNSToken()
			accountStatusValidService.isNotExistAccount(request.userId, request.sns, snsToken, request.email)
		} catch (e: Exception) {
			log().debug(e.stackTraceToString())
			return true
		}
		
		return false
	}
	
	// TODO AccountStatusModifyService.updateAccountStatus 를 MongoOperation 에서 postgreSQL 로 바꾼 뒤 주석 해제
//	@MutationMapping
//	suspend fun signOut(): String {
//		val accessToken = getToken()
//		val refreshToken = getRefreshToken()
//		val mac = getMac()
//
//		//TODO 남은 주문 처리 전부 환불 OR 배송 대기 정책 정하기
//		orderValidService.isNotExistLiveOrder(accessToken)
//
//		val userId = accountAuthDeleteService.deleteTokenInRedis(accessToken, refreshToken, mac)
//		accountStatusModifyService.updateAccountStatus(userId, Status.SIGNOUT)
//
//		return userId
//	}
	
	/**
	 * NORMAL: 일반 로그인 로직 실행
	 * SNS: Naver, Kakao 코드를 받아와 해당 코드로 토큰 발급, 그 후 인증한 뒤 유저 id를 가져와서 로그인 없는 경우 자동 가입 처리
	 */
	@MutationMapping
	suspend fun login(
		@Argument @Valid request: AccountLoginRequest,
		context: GraphQLContext
	): TokenResponse? {
		val ip = getIP()
		val agent = getAgent()
		val snsTokenHeader = getSNSToken()
		
		val snsToken = snsTokenHeader ?: accountStatusValidService.getSnsToken(request.sns, request.snsCode)
		if (!snsToken.isNullOrBlank())
			context.put("Authorization-SNS", snsToken)
		
		val accountId =
			accountStatusValidService.getLiveAccountIdByStatus(
				request.userId,
				request.sns,
				snsToken,
				request.email
			)
		
		val loginDTO = accountValidService.getAccountLoginDTO(accountId, request.pwd, request.sns)
		val tokenResponse = accountAuthCreateService.createTokenResponse(loginDTO, ip)
		
		context.put("Authorization", tokenResponse.accessToken)
		context.put("Authorization-Refresh", tokenResponse.refreshToken)
		
		return null
	}
	
	@MutationMapping
	suspend fun logout(): String {
		val accessToken = getToken()
		val refreshToken = getRefreshToken()
		val ip = getIP()
		
		val userId = accountAuthDeleteService.deleteTokenInRedis(accessToken, refreshToken, ip)
		
		return userId
	}
	
	@MutationMapping
	suspend fun reIssue(): TokenResponse {
		val token = getToken()
		val ip = getIP()
		
		return accountAuthCreateService.reIssueTokenResponse(ip, token)
	}
	
	@MutationMapping
	suspend fun changePassword(request: AccountChangePwdRequest): String {
		return accountChangePwdService.changePwd(
			request.userId,
			request.pwd,
			request.confirmPwd
		)
	}
}
