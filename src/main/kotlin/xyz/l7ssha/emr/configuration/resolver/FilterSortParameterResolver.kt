package xyz.l7ssha.emr.configuration.resolver

import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import xyz.l7ssha.emr.dto.pagination.*
import javax.servlet.http.HttpServletRequest

const val DEFAULT_PER_PAGE = 10
private const val QUERY_PARTS_COUNT_FOR_OPERATOR = 3

class FilterSortParameterResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == FilteringSortingInputDto::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.nativeRequest as? HttpServletRequest ?: return null

        val page = (request.getParameterValues("page")?.first()?.toInt()?.minus(1) ?: 0)
            .coerceAtLeast(0)
        val perPage = request.getParameterValues("perPage")?.first()?.toInt() ?: DEFAULT_PER_PAGE

        val filters = mutableListOf<FilterInputDto>()
        val sorting = mutableListOf<SortingInputDto>()

        request.parameterMap.forEach { (key, values) ->
            when {
                key.startsWith("filter") -> filters.add(parseFilterInputString(key, values.first()))
                key.startsWith("sort") -> sorting.add(parseSortingInputString(key, values.first()))
            }
        }

        return FilteringSortingInputDto(
            perPage,
            page,
            filters,
            sorting
        )
    }

    private fun parseSortingInputString(input: String, value: String): SortingInputDto {
        val parts = input.split(",")

        return SortingInputDto(parts.last(), SortingOperator.valueOf(value))
    }

    private fun parseFilterInputString(input: String, value: String): FilterInputDto {
        val parts = input.split(",")

        return FilterInputDto(
            parts[1],
            value,
            if (parts.count() == QUERY_PARTS_COUNT_FOR_OPERATOR) FilteringOperator.valueOf(parts[2]) else null
        )
    }
}
