package sosteam.deamhome.domain.auth.handler

import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import sosteam.deamhome.domain.account.service.CreateAccountService
import sosteam.deamhome.domain.auth.handler.request.SignUpRequest

@Controller
@RequiredArgsConstructor
class AuthResolver(private val createAccountService: CreateAccountService) {
	
	@MutationMapping
	fun signUp(@Argument @Valid request: SignUpRequest): String {
		
		
		return "testId"
	}
}