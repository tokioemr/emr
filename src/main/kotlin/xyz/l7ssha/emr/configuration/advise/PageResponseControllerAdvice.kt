package xyz.l7ssha.emr.configuration.advise

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import xyz.l7ssha.emr.mapper.PaginationMapper

@ControllerAdvice
class PageResponseControllerAdvice(@Autowired val paginationMapper: PaginationMapper) : ResponseBodyAdvice<Any?> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        // TODO Powinno byc sprawdzane czy type jest Page<*>
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        if (body !is Page<*>) {
            return body
        }

        return paginationMapper.pageToPageOutputDto(body)
    }
}
