package sosteam.deamhome.domain.kakao.resolver

import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import sosteam.deamhome.domain.kakao.dto.response.KakaoTokenReturnResponse
import sosteam.deamhome.domain.kakao.dto.response.KakaoUnlinkResponse
import sosteam.deamhome.domain.kakao.dto.response.KakaoUserInfo
import sosteam.deamhome.domain.kakao.service.KakaoService

@Controller
@Slf4j
@RequiredArgsConstructor
class KakaoResolver(
    private val kakaoService: KakaoService
) {
    @QueryMapping
    suspend fun kakaoLoginUrl(): String {
        return kakaoService.getKakaoLoginPage()
    }

    @MutationMapping
    suspend fun kakaoSignUp(@Argument @Valid code: String): KakaoTokenReturnResponse {
        return kakaoService.kakaoSign(code)
    }

    @QueryMapping
    suspend fun kakaoUserInfo(@Argument @Valid token: String): KakaoUserInfo {
        return kakaoService.getKakaoUserInfo(token)
    }

    @MutationMapping
    suspend fun kakaoUnlink(@Argument @Valid token: String): KakaoUnlinkResponse {
        return kakaoService.unlinkKakao(token)
    }
}