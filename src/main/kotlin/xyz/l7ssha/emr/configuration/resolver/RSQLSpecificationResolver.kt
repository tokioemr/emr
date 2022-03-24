package xyz.l7ssha.emr.configuration.resolver

import io.github.perplexhub.rsql.RSQLJPASupport
import org.springframework.core.MethodParameter
import org.springframework.data.jpa.domain.Specification
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import xyz.l7ssha.emr.dto.RSQLSpecification
import xyz.l7ssha.emr.entities.AbstractSoftDelete

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

        val filters = Specification.where<Any> { root, _, criteriaBuilder ->
            if (!AbstractSoftDelete::class.java.isAssignableFrom(root.model.javaType)) {
                return@where criteriaBuilder.conjunction()
            }

            criteriaBuilder.isNull(root.get<Any>("deletedAt"))
        }

        val filtersParamValue = request.getParameter("filter")
        if (filtersParamValue != null) {
            filters.and(RSQLJPASupport.toSpecification(filtersParamValue))
        }

        val sortingParamValue = request.getParameter("sort")
        val sorting = if (sortingParamValue != null)
            RSQLJPASupport.toSort<Any>(sortingParamValue)
        else null

        return RSQLSpecification<Any>(filters, sorting)
    }
}
