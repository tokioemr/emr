package xyz.l7ssha.emr.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.exception.PostValidationException
import xyz.l7ssha.emr.configuration.security.AuthenticationFacade
import xyz.l7ssha.emr.dto.category.CategoryCreateInputDto
import xyz.l7ssha.emr.dto.category.CategoryOutputDto
import xyz.l7ssha.emr.dto.category.CategoryPatchInputDto
import xyz.l7ssha.emr.entities.products.Category
import xyz.l7ssha.emr.repositories.CategoryRepository
import java.time.Instant

@Component
class CategoryMapper(
    @Autowired val categoryRepository: CategoryRepository,
    @Autowired val authenticationFacade: AuthenticationFacade
) {
    fun categoryToOutputDto(category: Category): CategoryOutputDto =
        CategoryOutputDto(
            category.id,
            category.name,
            category.parent?.let { categoryToOutputDto(it) },
            category.assignable
        )

    fun updateCategoryFromPatchDto(category: Category, patchDto: CategoryPatchInputDto): Category {
        return category.apply {
            patchDto.name.ifPresent {
                this.name = it
            }

            patchDto.parent.ifPresent {
                if (it == null) {
                    this.parent = null
                }

                val parentCategory = categoryRepository.findById(it).orElseThrow {
                    PostValidationException("Category with id: '$it' does not exists")
                }

                this.parent = parentCategory
            }

            updatedBy = authenticationFacade.loggedInUser
            updatedAt = Instant.now()
        }
    }

    fun categoryCreateDtoToCategory(categoryInput: CategoryCreateInputDto): Category {
        val parentCategory = categoryInput.parent?.let {
            categoryRepository.findById(it).orElseThrow {
                PostValidationException("Category with id: '${categoryInput.parent}' does not exists")
            }
        }

        return Category(
            0L,
            categoryInput.name,
            parentCategory,
            assignable = categoryInput.assignable,
        ).apply {
            createdBy = authenticationFacade.loggedInUser
        }
    }
}
