package xyz.l7ssha.emr.controller.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import xyz.l7ssha.emr.dto.RSQLSpecification
import xyz.l7ssha.emr.entities.products.Category
import xyz.l7ssha.emr.mapper.CategoryMapper
import xyz.l7ssha.emr.repositories.CategoryRepository

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    @Autowired val categoryRepository: CategoryRepository,
    @Autowired val categoryMapper: CategoryMapper
) {
    @GetMapping
    fun getAllAction(specification: RSQLSpecification<Category>, pageable: Pageable) =
        categoryRepository.findAll(specification.getFiltersAndSpecification(), pageable)
            .map { categoryMapper.categoryToOutputDto(it) }

    @GetMapping("/{id}")
    fun getItemAction(@PathVariable id: Long) =
        categoryRepository.getById(id).let { categoryMapper.categoryToOutputDto(it) }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_CATEGORIES')")
    fun postItemAction(): Nothing = TODO()

    @PatchMapping
    @PreAuthorize("hasAuthority('CREATE_CATEGORIES')")
    fun patchItemAction(): Nothing = TODO()

    @DeleteMapping
    @PreAuthorize("hasAuthority('REMOVE_CATEGORIES')")
    fun removeItemAction(): Nothing = TODO()
}
