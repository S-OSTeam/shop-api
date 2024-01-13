package sosteam.deamhome.global.security.service

import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service


@Service
class AuthenticationService {
    // Todo: 수정 필요,,  커스텀 에러 필요하면 교체
    suspend fun getUserIdFromToken(): String {

        return ReactiveSecurityContextHolder.getContext()
            .map(SecurityContext::getAuthentication)
            .map { it?.principal }
            .map { principal ->
                when (principal) {
                    is UserDetails -> principal.username
                    is String -> principal
                    else -> throw RuntimeException("Unexpected principal type: $principal")
                }
            }
            .awaitSingleOrNull() ?: throw RuntimeException("User ID not found.")
    }
}