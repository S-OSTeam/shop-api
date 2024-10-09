package sosteam.deamhome.domain.auth.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.auth.exception.TokenNotValidException
import sosteam.deamhome.global.attribute.Token
import sosteam.deamhome.global.security.provider.JWTProvider
import sosteam.deamhome.global.security.provider.RedisProvider

@Service
@Transactional
class AccountAuthDeleteService(
	private val jwtProvider: JWTProvider,
	private val redisProvider: RedisProvider
) {
	//token response를 만들어 주는 함수
	suspend fun deleteTokenInRedis(access: String, refresh: String, mac: String): String {
		
		if (!jwtProvider.isValid(access, mac, Token.ACCESS) || !jwtProvider.isValid(refresh, mac, Token.REFRESH)) {
			throw TokenNotValidException()
		}
		
		//토큰 날짜를 통일 시키기 위해 현재 시간을 저장
		val leftAccessTokenTime = jwtProvider.getLeftTime(access)
		val userId = jwtProvider.getUserId(access)
		
		//기존에 존재하던 refresh 토큰 삭제
		redisProvider.deleteData(userId)
		
		if (leftAccessTokenTime > 0) {
			//redis에 accessToken 사용 못하도록 등록
			redisProvider.setDataExpire(access, userId, leftAccessTokenTime)
		}
		
		return userId
	}
}