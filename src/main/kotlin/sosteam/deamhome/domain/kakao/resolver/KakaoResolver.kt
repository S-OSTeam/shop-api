package sosteam.deamhome.domain.kakao.resolver

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
import sosteam.deamhome.domain.kakao.dto.request.KakaoLoginRequest
import sosteam.deamhome.domain.kakao.dto.response.KakaoTokenReturnResponse
import sosteam.deamhome.domain.kakao.dto.response.KakaoUnlinkResponse
import sosteam.deamhome.domain.kakao.dto.response.KakaoUserInfo
import sosteam.deamhome.domain.kakao.service.KakaoService
import sosteam.deamhome.global.provider.RequestProvider
import sosteam.deamhome.global.provider.RequestProvider.Companion.getAgent
import sosteam.deamhome.global.provider.RequestProvider.Companion.getMac
import sosteam.deamhome.global.security.response.TokenResponse

@Controller
@Slf4j
@RequiredArgsConstructor
class KakaoResolver(
    private val kakaoService: KakaoService,
    private val accountAuthCreateService: AccountAuthCreateService
) {
    @QueryMapping
    suspend fun kakaoLoginUrl(): String {
        return kakaoService.getKakaoLoginPage()
    }

    @MutationMapping
    suspend fun kakaoLogin(
        @Argument request: KakaoLoginRequest,
        context: GraphQLContext
    ): TokenResponse? {
        val mac = getMac()
        val agent = getAgent()
        val account = kakaoService.kakaoSign(request.code, request.state)

        val loginDTO = AccountLoginDTO.fromDomain(account)
        val tokenResponse = accountAuthCreateService.createTokenResponse(loginDTO, mac)

        if(agent.contains("Mobile")) {
            return tokenResponse
        }
        context.put("accessToken", tokenResponse.accessToken)
        context.put("refreshToken", tokenResponse.refreshToken)

        return null
    }


    @QueryMapping
    suspend fun kakaoUserInfo(@Argument request: KakaoLoginRequest): KakaoUserInfo {
        return kakaoService.getKakaoUserInfo(kakaoService.getKakaoToken(request.code, request.state).accessToken)
    }

    @MutationMapping
    suspend fun kakaoUnlink(@Argument @Valid token: String): KakaoUnlinkResponse {
        return kakaoService.unlinkKakao(token)
    }
}