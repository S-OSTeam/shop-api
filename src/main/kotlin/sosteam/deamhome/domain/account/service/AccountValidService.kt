package sosteam.deamhome.domain.account.service

import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sosteam.deamhome.domain.account.entity.Account
import sosteam.deamhome.domain.account.exception.AccountNotFoundException
import sosteam.deamhome.domain.account.exception.LoginFailureException
import sosteam.deamhome.domain.account.repository.AccountRepository
import sosteam.deamhome.domain.auth.entity.dto.AccountLoginDTO
import sosteam.deamhome.domain.auth.handler.request.AccountCreateRequest
import sosteam.deamhome.global.attribute.SNS
import sosteam.deamhome.global.attribute.Token
import sosteam.deamhome.global.exception.PasswordNotMatchedException
import sosteam.deamhome.global.provider.RequestProvider.Companion.getIP
import sosteam.deamhome.global.security.provider.JWTProvider
import sosteam.deamhome.global.security.provider.RedisProvider
import sosteam.deamhome.global.security.response.TokenResponse
import java.util.*

@Service
@Transactional
@RequiredArgsConstructor
class AccountValidService(
	private val accountRepository: AccountRepository,
	private val passwordEncoder: PasswordEncoder,
	private val jwtProvider: JWTProvider,
	private val redisProvider: RedisProvider
) {
	
	//userId로 account 가져오기
	suspend fun getAccountByUserId(userId: String): Account {
		return accountRepository.findAccountByUserId(userId)
			?: throw AccountNotFoundException()
	}
	
	suspend fun getAccountLoginDTO(id: Long, pwd: String?, sns: SNS): AccountLoginDTO {
		val account = accountRepository.findAccountById(id) ?: throw LoginFailureException()
		
		if (sns == SNS.NORMAL && !passwordEncoder.matches(pwd, account.pwd)) {
			throw LoginFailureException()
		}
		
		return AccountLoginDTO.fromDomain(account)
	}
	
	suspend fun getAccountByUserName(userName: String): Account? {
		return accountRepository.findAccountByUserName(userName)
	}
	
	suspend fun isValidAccount(accountCreateRequest: AccountCreateRequest): Boolean {
		if (accountCreateRequest.pwd != accountCreateRequest.confirmPwd)
			throw PasswordNotMatchedException()
		
		return true
	}
	
	suspend fun issueJwtToken(accountLoginDTO: AccountLoginDTO): TokenResponse {
		val ip = getIP()
		val token = jwtProvider.generate(
			accountLoginDTO.userId,
			accountLoginDTO.authorities,
			ip, Date(System.currentTimeMillis())
		)
		redisProvider.setDataExpire(accountLoginDTO.userId, token.refreshToken, Token.REFRESH.time)
		return token
	}
	
}