package xyz.l7ssha.emr.configuration.resolver

import io.github.perplexhub.rsql.RSQLJPASupport
import org.springframework.core.MethodParameter
import org.springframework.data.jpa.domain.Specification
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

data class RSQLSpecification<T>(val filters: Specification<T>?, val sorting: Specification<T>?) {
    fun getFiltersAndSpecification(): Specification<T>? {
        if (filters != null) {
            return filters.and(sorting)
        }

        return sorting
    }
}

class RSQLSpecificationResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == RSQLSpecification::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val request = (webRequest as ServletWebRequest).request

        val filtersParamValue = request.getParameter("filter")
        val filters = if (filtersParamValue != null)
            RSQLJPASupport.toSpecification<Any>(filtersParamValue)
        else null

        val sortingParamValue = request.getParameter("sort")
        val sorting = if (sortingParamValue != null)
            RSQLJPASupport.toSort<Any>(sortingParamValue)
        else null

        return RSQLSpecification<Any>(filters, sorting)
    }
}
