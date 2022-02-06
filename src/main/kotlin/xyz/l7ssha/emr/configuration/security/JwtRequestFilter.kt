package xyz.l7ssha.emr.configuration.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.filter.OncePerRequestFilter
import xyz.l7ssha.emr.service.AuthService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtRequestFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var authService: AuthService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        authService.validateJwtForRequest(request)

        filterChain.doFilter(request, response)
    }
}
