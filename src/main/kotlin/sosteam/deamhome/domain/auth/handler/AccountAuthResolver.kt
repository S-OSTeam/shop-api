package sosteam.deamhome.domain.auth.handler

import graphql.GraphQLContext
import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.account.exception.PwdAndConfirmPwdNotMachedException
import sosteam.deamhome.domain.account.service.*
import sosteam.deamhome.domain.auth.handler.request.AccountChangePwdRequest
import sosteam.deamhome.domain.auth.handler.request.AccountCreateRequest
import sosteam.deamhome.domain.auth.handler.request.AccountLoginRequest
import sosteam.deamhome.domain.auth.service.AccountAuthCreateService
import sosteam.deamhome.domain.auth.service.AccountAuthDeleteService
import sosteam.deamhome.domain.order.service.OrderValidService
import sosteam.deamhome.global.attribute.Status
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
	val orderValidService: OrderValidService,
	val accountStatusValidService: AccountStatusValidService,
	val accountStatusModifyService: AccountStatusModifyService,
	val accountChangePwdService: AccountChangePwdService
) {
	@MutationMapping
	suspend fun signUp(@Argument @Valid request: AccountCreateRequest): String {
		val mac = getMac()
		if(request.pwd != request.confirmPwd) throw PwdAndConfirmPwdNotMachedException()
		accountStatusValidService.isNotExistAccount(
			request.userId,
			request.sns,
			request.snsId,
			request.email
		)
		
		val createAccount = accountCreateService.createAccount(request)
		return createAccount.userId
	}
	
	@MutationMapping
	suspend fun signOut(): String {
		val accessToken = getToken()
		val refreshToken = getRefreshToken()
		val mac = getMac()

		orderValidService.isNotExistLiveOrder(accessToken)
		
		val userId = accountAuthDeleteService.deleteTokenInRedis(accessToken, refreshToken, mac)
		accountStatusModifyService.updateAccountStatus(userId, Status.SIGNOUT)
		
		return userId
	}
	
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
				request.sns,
				request.snsId,
				request.email
			)
		
		val loginDTO = accountValidService.getAccountLoginDTO(accountId, request.pwd)
		val tokenResponse = accountAuthCreateService.createTokenResponse(loginDTO, mac)

		if(agent.contains("Mobile")) {
			return tokenResponse
		}
		context.put("accessToken", tokenResponse.accessToken)
		context.put("refreshToken", tokenResponse.refreshToken)

		return null
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
		return accountChangePwdService.changePwd(
			request.email,
			request.pwd,
			request.confirmPwd
		)
	}
}