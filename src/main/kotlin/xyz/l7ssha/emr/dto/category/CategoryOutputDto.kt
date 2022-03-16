package xyz.l7ssha.emr.dto.category

data class CategoryOutputDto(val id: Long, val name: String, val parent: CategoryOutputDto?, val assignable: Boolean)
