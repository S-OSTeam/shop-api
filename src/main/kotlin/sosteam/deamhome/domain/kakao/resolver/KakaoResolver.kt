package sosteam.deamhome.domain.kakao.resolver

import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import sosteam.deamhome.domain.auth.service.AccountAuthCreateService
import sosteam.deamhome.domain.kakao.dto.response.KakaoUnlinkResponse
import sosteam.deamhome.domain.kakao.service.KakaoService

@Controller
@Slf4j
@RequiredArgsConstructor
class KakaoResolver(
	private val kakaoService: KakaoService,
	private val accountAuthCreateService: AccountAuthCreateService
) {
	
	@MutationMapping
	suspend fun kakaoUnlink(@Argument @Valid token: String): KakaoUnlinkResponse {
		return kakaoService.unlinkKakao(token)
	}
}