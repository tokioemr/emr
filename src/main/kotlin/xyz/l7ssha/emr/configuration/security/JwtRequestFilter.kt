package xyz.l7ssha.emr.configuration.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.filter.OncePerRequestFilter
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationException
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationExceptionWithData
import xyz.l7ssha.emr.mapper.ExceptionMapper
import xyz.l7ssha.emr.service.AuthService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtRequestFilter : OncePerRequestFilter() {
    @Autowired
    lateinit var authService: AuthService

    @Autowired
    lateinit var exceptionMapper: ExceptionMapper

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            authService.validateJwtForRequest(request)
        } catch (exception: CatchableApplicationException) {
            response.status = exception.status.value()
            response.writer.write(convertObjectToJson(exceptionMapper.catchableExceptionToDto(exception)))
        } catch (exceptionWithData: CatchableApplicationExceptionWithData) {
            response.status = exceptionWithData.status.value()
            response.writer.write(convertObjectToJson(exceptionMapper.catchableExceptionToDto(exceptionWithData)))
        }

        filterChain.doFilter(request, response)
    }

    private fun convertObjectToJson(obj: Any): String {
        return ObjectMapper().writeValueAsString(obj)
    }
}
