package xyz.l7ssha.emr.mapper

import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.dto.pagination.PageOutputDto

@Component
class PaginationMapper {
    fun <T> pageToPageOutputDto(page: Page<T>): PageOutputDto<T> {
        return PageOutputDto(
            page.content,
            page.number.toLong(),
            page.pageable.pageSize.toLong(),
            page.numberOfElements.toLong()
        )
    }
}
