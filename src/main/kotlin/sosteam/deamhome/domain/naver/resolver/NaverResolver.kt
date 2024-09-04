package sosteam.deamhome.domain.naver.resolver

import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import sosteam.deamhome.domain.naver.dto.response.NaverUnlinkResponse
import sosteam.deamhome.domain.naver.service.NaverService

@Controller
@Slf4j
@RequiredArgsConstructor
class NaverResolver(
	private val naverService: NaverService
) {
	
	@MutationMapping
	suspend fun naverUnlink(@Argument @Valid snsCode: String): NaverUnlinkResponse {
		return naverService.unlinkNaver(snsCode)
	}
}