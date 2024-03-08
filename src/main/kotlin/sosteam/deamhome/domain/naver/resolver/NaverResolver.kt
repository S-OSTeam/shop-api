package sosteam.deamhome.domain.naver.resolver

import graphql.GraphQLContext
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import sosteam.deamhome.domain.auth.entity.dto.AccountLoginDTO
import sosteam.deamhome.domain.auth.service.AccountAuthCreateService
import sosteam.deamhome.domain.naver.dto.request.NaverRequest
import sosteam.deamhome.domain.naver.dto.response.NaverUnlinkResponse
import sosteam.deamhome.domain.naver.dto.response.NaverUserInfo
import sosteam.deamhome.domain.naver.service.NaverService
import sosteam.deamhome.global.provider.RequestProvider
import sosteam.deamhome.global.security.response.TokenResponse

@Controller
@Slf4j
@RequiredArgsConstructor
class NaverResolver(
	private val naverService: NaverService,
	private val accountAuthCreateService: AccountAuthCreateService
) {
	@QueryMapping
	suspend fun naverLoginUrl(): String {
		return naverService.getNaverLoginPage()
	}
	
	@MutationMapping
	suspend fun naverLogin(
		@Argument request: NaverRequest,
		context: GraphQLContext
	): TokenResponse? {
		val mac = RequestProvider.getMac()
		val agent = RequestProvider.getAgent()
		val account = naverService.naverSign(request.code, request.state)
		
		val loginDTO = AccountLoginDTO.fromDomain(account)
		val tokenResponse = accountAuthCreateService.createTokenResponse(loginDTO, mac)
		
		if (agent.contains("Mobile")) {
			return tokenResponse
		}
		context.put("accessToken", tokenResponse.accessToken)
		context.put("refreshToken", tokenResponse.refreshToken)
		
		return null
	}
	
	@QueryMapping
	suspend fun naverUserInfo(@Argument request: NaverRequest): NaverUserInfo {
		return naverService.getNaverUserInfo(naverService.getNaverToken(request.code, request.state).accessToken!!)
	}
	
	@MutationMapping
	suspend fun naverUnlink(@Argument @Valid token: String): NaverUnlinkResponse {
		return naverService.unlinkNaver(token)
	}
}