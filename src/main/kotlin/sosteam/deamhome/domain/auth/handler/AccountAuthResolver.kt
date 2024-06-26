package sosteam.deamhome.domain.auth.handler

import graphql.GraphQLContext
import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.account.service.*
import sosteam.deamhome.domain.auth.handler.request.AccountChangePwdRequest
import sosteam.deamhome.domain.auth.handler.request.AccountCreateRequest
import sosteam.deamhome.domain.auth.handler.request.AccountLoginRequest
import sosteam.deamhome.domain.auth.service.AccountAuthCreateService
import sosteam.deamhome.domain.auth.service.AccountAuthDeleteService
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.provider.RequestProvider.Companion.getAgent
import sosteam.deamhome.global.provider.RequestProvider.Companion.getMac
import sosteam.deamhome.global.provider.RequestProvider.Companion.getRefreshToken
import sosteam.deamhome.global.provider.RequestProvider.Companion.getToken
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
		val mac = getMac()

		accountStatusValidService.isNotExistAccount(
			request.userId,
			request.sns,
			request.snsId,
			request.email
		)

		val createAccount = accountCreateService.createAccount(request)
		return createAccount.userId
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

	@MutationMapping
	suspend fun login(
		@Argument @Valid request: AccountLoginRequest,
		context: GraphQLContext
	): TokenResponse? {
		val mac = getMac()
		val agent = getAgent()

		val accountId =
			accountStatusValidService.getLiveAccountIdByStatus(
				request.userId,
				SNS.NORMAL,
				null,
				request.email
			)

		val loginDTO = accountValidService.getAccountLoginDTO(accountId, request.pwd)
		val tokenResponse = accountAuthCreateService.createTokenResponse(loginDTO, mac)

		if(agent.contains("Mobile")) {
			return tokenResponse
		}
		context.put("accessToken", tokenResponse.accessToken)
		context.put("refreshToken", tokenResponse.refreshToken)

		// 일단 토큰 반환하도록 설정
		return tokenResponse
	}

	@MutationMapping
	suspend fun logout(): String {
		val accessToken = getToken()
		val refreshToken = getRefreshToken()
		val mac = getMac()

		val userId = accountAuthDeleteService.deleteTokenInRedis(accessToken, refreshToken, mac)

		return userId
	}

	@MutationMapping
	suspend fun reIssue(): TokenResponse {
		val token = getToken()
		val mac = getMac()

		return accountAuthCreateService.reIssueTokenResponse(mac, token)
	}

	@MutationMapping
	suspend fun changePassword(request: AccountChangePwdRequest): String {
		val mac = getMac()
		return accountChangePwdService.changePwd(
			request.userId,
			request.pwd,
			request.confirmPwd
		)
	}
}