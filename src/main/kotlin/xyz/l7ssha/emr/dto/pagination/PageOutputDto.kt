package xyz.l7ssha.emr.dto.pagination

data class PageOutputDto<T>(val data: List<T>, val page: Long, val perPage: Long, val total: Long)
