package sosteam.deamhome.global.provider

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import sosteam.deamhome.domain.auth.exception.TokenNotFoundException
import sosteam.deamhome.global.exception.AgentNotFoundException
import sosteam.deamhome.global.exception.MacNotFoundException
import sosteam.deamhome.global.log.ReactiveRequestContextHolder

@Component
class RequestProvider {
	
	companion object {
		
		//token을 가져오는 함수
		suspend fun getToken(): String {
			val headers = ReactiveRequestContextHolder.getRequest().awaitSingle().headers
			val token = headers.getFirst(HttpHeaders.AUTHORIZATION)
			
			if (token.isNullOrEmpty() || !token.startsWith("DBearer "))
				throw TokenNotFoundException()
			
			return token.substring(8)
		}
		
		//token을 가져오는 함수
		suspend fun getRefreshToken(): String {
			val headers = ReactiveRequestContextHolder.getRequest().awaitSingle().headers
			val token = headers.getFirst("Authorization-Refresh")
			
			if (token.isNullOrEmpty() || !token.startsWith("DBearer "))
				throw TokenNotFoundException()
			
			return token.substring(8)
		}
		
		//mac주소를 가져오는 함수
		suspend fun getMac(): String {
			val headers = ReactiveRequestContextHolder.getRequest().awaitSingle().headers
			val mac = headers.getFirst("Authorization-Mac")
			
			if (mac.isNullOrEmpty() || mac.length != 17) {
				throw MacNotFoundException()
			}
			
			return mac
		}
		// getUserAgent
		suspend fun getAgent(): String {
			val headers = ReactiveRequestContextHolder.getRequest().awaitSingle().headers
			val agent = headers.getFirst("User-Agent")

			if(agent.isNullOrEmpty() || (agent!="Web" && agent!="Mobile")) {
				throw AgentNotFoundException()
			}
			return agent
		}
	}
}