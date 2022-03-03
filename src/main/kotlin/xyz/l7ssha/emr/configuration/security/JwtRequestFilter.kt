package xyz.l7ssha.emr.configuration.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.filter.OncePerRequestFilter
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationException
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationWithDataException
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
            writeResponse(
                response,
                convertObjectToJson(exceptionMapper.catchableExceptionToDto(exception)),
                exception.status.value(),
            )

            return
        } catch (exceptionWithData: CatchableApplicationWithDataException) {
            writeResponse(
                response,
                convertObjectToJson(exceptionMapper.catchableExceptionToDto(exceptionWithData)),
                exceptionWithData.status.value(),
            )

            return
        }

        filterChain.doFilter(request, response)
    }

    private fun writeResponse(response: HttpServletResponse, body: String, status: Int) {
        response.status = status
        response.writer.write(body)
        response.addHeader("Content-Type", "application/json")
        response.writer.flush()
    }

    private fun convertObjectToJson(obj: Any): String {
        return ObjectMapper().writeValueAsString(obj)
    }
}
