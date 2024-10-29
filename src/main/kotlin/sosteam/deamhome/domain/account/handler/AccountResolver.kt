package sosteam.deamhome.domain.account.handler

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.account.handler.response.AccountResponse
import sosteam.deamhome.domain.account.service.AccountReadService
import sosteam.deamhome.global.attribute.Token
import sosteam.deamhome.global.provider.RequestProvider.Companion.getToken
import sosteam.deamhome.global.security.provider.JWTProvider
import sosteam.deamhome.global.security.service.AuthenticationService

@RestController
class AccountResolver(
	private val accountReadService: AccountReadService,
	private val authenticationService: AuthenticationService,
	private val jwtProvider: JWTProvider
) {
	@QueryMapping
	// 로그인한 사용자 정보 가져오기
	suspend fun getAccountInfo(): AccountResponse {
		val token = getToken()
		return accountReadService.getAccount(jwtProvider.getUserId(token, Token.ACCESS))
	}
}