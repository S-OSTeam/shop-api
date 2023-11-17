package sosteam.deamhome.domain.naver.handler

import jakarta.validation.Valid
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import sosteam.deamhome.domain.naver.handler.request.AuthorizationCode
import sosteam.deamhome.domain.naver.handler.response.UrlResponse
import sosteam.deamhome.domain.naver.service.NaverLoginService
import sosteam.deamhome.global.security.provider.RandomKeyProvider
import sosteam.deamhome.global.security.response.TokenResponse

@Controller
class NaverResolver(
    private val naverLoginService: NaverLoginService,
    private val randomKeyProvider: RandomKeyProvider
) {
    @QueryMapping
    suspend fun getNaverUrl(): UrlResponse {
        return naverLoginService.naverConnect(randomKeyProvider)
    }

    @QueryMapping
    suspend fun getNaverLogin(@Argument @Valid authorizationCode: AuthorizationCode): TokenResponse {
        return naverLoginService.naverLogin(authorizationCode.code, authorizationCode.state)
    }
}