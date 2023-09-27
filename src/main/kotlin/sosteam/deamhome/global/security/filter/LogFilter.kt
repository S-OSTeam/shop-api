package sosteam.deamhome.global.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import sosteam.deamhome.global.provider.log

@Component
class LogFilter : OncePerRequestFilter() {
	
	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain
	) {
		log().info("[{}] {}", request.method, request.requestURI)
		filterChain.doFilter(request, response)
	}
}