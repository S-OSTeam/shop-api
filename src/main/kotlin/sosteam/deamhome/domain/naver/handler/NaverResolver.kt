package sosteam.deamhome.domain.naver.handler

import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import sosteam.deamhome.domain.naver.handler.response.NaverTokenReturnResponse
import sosteam.deamhome.domain.naver.handler.response.NaverUnlinkResponse
import sosteam.deamhome.domain.naver.handler.response.NaverUserInfo
import sosteam.deamhome.domain.naver.service.NaverService

@Controller
class NaverResolver(
	private val naverService: NaverService,
) {
	@QueryMapping
	suspend fun naverLoginUrl(): String {
		return naverService.getNaverLoginPage()
	}
	
	@MutationMapping
	suspend fun naverSignUp(@Argument @Valid code: String, @Argument @Valid state: String): NaverTokenReturnResponse {
		return naverService.naverSign(code, state)
	}
	
	@QueryMapping
	suspend fun naverUserInfo(@Argument @Valid token: String): NaverUserInfo {
		return naverService.getNaverUserInfo(token)
	}
	
	@MutationMapping
	suspend fun naverUnlink(@Argument @Valid token: String): NaverUnlinkResponse {
		return naverService.unlinkNaver(token)
	}
}