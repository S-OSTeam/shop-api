package sosteam.deamhome.domain.account.handler

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController
import sosteam.deamhome.domain.account.handler.response.AccountResponse
import sosteam.deamhome.domain.account.service.AccountReadService
import sosteam.deamhome.global.security.service.AuthenticationService

@RestController
class AccountResolver (
    private val accountReadService: AccountReadService,
    private val authenticationService: AuthenticationService,
){
    @QueryMapping
    // 로그인한 사용자 정보 가져오기
    suspend fun getAccountInfo(): AccountResponse{
        return accountReadService.getAccount(authenticationService.getUserIdFromToken())
    }
}