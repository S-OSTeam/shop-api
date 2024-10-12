package sosteam.deamhome.global.provider

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import sosteam.deamhome.domain.auth.exception.TokenNotFoundException
import sosteam.deamhome.global.exception.AgentNotFoundException
import sosteam.deamhome.global.exception.IpNotFoundException
import sosteam.deamhome.global.log.ReactiveRequestContextHolder

@Component
class RequestProvider {
	
	companion object {
		
		//token을 가져오는 함수
		suspend fun getToken(): String {
			val cookies = ReactiveRequestContextHolder.getRequest().awaitSingle().cookies
			val headers = ReactiveRequestContextHolder.getRequest().awaitSingle().headers
			val token = cookies.getFirst("Authorization")?.value ?: headers.getFirst("Authorization")
			
			if (token.isNullOrEmpty() || !token.startsWith("DBearer+"))
				throw TokenNotFoundException()
			
			return token.substring(8)
		}
		
		//token을 가져오는 함수
		suspend fun getRefreshToken(): String {
			val cookies = ReactiveRequestContextHolder.getRequest().awaitSingle().cookies
			val headers = ReactiveRequestContextHolder.getRequest().awaitSingle().headers
			val token = cookies.getFirst("Authorization-Refresh")?.value ?: headers.getFirst("Authorization-Refresh")
			
			if (token.isNullOrEmpty() || !token.startsWith("DBearer+"))
				throw TokenNotFoundException()
			
			return token.substring(8)
		}
		
		//token을 가져오는 함수
		suspend fun getSNSToken(): String? {
			val cookies = ReactiveRequestContextHolder.getRequest().awaitSingle().cookies
			val token = cookies.getFirst("Authorization-SNS")?.value
			
			return token
		}
		
		//IP주소를 가져오는 함수
		suspend fun getIP(): String {
			val headers = ReactiveRequestContextHolder.getRequest().awaitSingle().headers
			val ip = headers.getFirst("X-Real-IP") ?: headers.getFirst("X-Forwarded-For")
			
			if (ip.isNullOrEmpty()) {
				throw IpNotFoundException()
			}
			
			return ip
		}
		
		// getUserAgent
		suspend fun getAgent(): String {
			val headers = ReactiveRequestContextHolder.getRequest().awaitSingle().headers
			val agent = headers.getFirst("User-Agent")
			
			if (agent.isNullOrEmpty()) {
				throw AgentNotFoundException()
			}
			return agent
		}
	}
}