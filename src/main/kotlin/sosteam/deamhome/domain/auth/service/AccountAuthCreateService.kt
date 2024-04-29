package sosteam.deamhome.domain.auth.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.auth.entity.dto.AccountLoginDTO
import sosteam.deamhome.domain.auth.exception.TokenNotValidException
import sosteam.deamhome.global.attribute.Token
import sosteam.deamhome.global.security.provider.JWTProvider
import sosteam.deamhome.global.security.provider.RedisProvider
import sosteam.deamhome.global.security.response.TokenResponse
import java.util.*

@Service
@Transactional
class AccountAuthCreateService(
	private val jwtProvider: JWTProvider,
	private val redisProvider: RedisProvider
) {
	suspend fun reIssueTokenResponse(mac: String, refresh: String): TokenResponse {
		val userId = jwtProvider.getData(refresh)
		val inRedisToken = redisProvider.getData(userId)

		//토큰이 옳바른지, redis에 존재하는지를 확인
		if (!jwtProvider.isValid(refresh, mac, Token.REFRESH) || inRedisToken != refresh) {
			throw TokenNotValidException()
		}

		//refresh 토큰을 한 번 사용 했으므로 삭제 후 재발급
		redisProvider.deleteData(userId)

		val authorities = jwtProvider.getSimpleGrantedAuthority(refresh)

		return createTokenResponse(AccountLoginDTO(userId, authorities), mac)
	}


	//token response를 만들어 주는 함수
	suspend fun createTokenResponse(dto: AccountLoginDTO, mac: String): TokenResponse {
		//토큰 날짜를 통일 시키기 위해 현재 시간을 저장
		val issuedAt = Date(System.currentTimeMillis())
		val tokenResponse = jwtProvider.generate(
			dto.userId,
			dto.authorities, mac, issuedAt
		)

		//redis에 refresh토큰 등록
		redisProvider.setDataExpire(dto.userId, tokenResponse.refreshToken, Token.REFRESH.time)

		return tokenResponse
	}
}