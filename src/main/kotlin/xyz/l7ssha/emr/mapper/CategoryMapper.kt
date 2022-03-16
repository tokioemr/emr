package xyz.l7ssha.emr.mapper

import org.springframework.stereotype.Component
import xyz.l7ssha.emr.dto.category.CategoryOutputDto
import xyz.l7ssha.emr.entities.products.Category

@Component
class CategoryMapper {
    fun categoryToOutputDto(category: Category): CategoryOutputDto =
        CategoryOutputDto(
            category.id,
            category.name,
            category.parent?.let { categoryToOutputDto(it)},
            category.assignable
        )
}
