package xyz.l7ssha.emr.controller.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import xyz.l7ssha.emr.configuration.security.AuthenticationFacade
import xyz.l7ssha.emr.dto.RSQLSpecification
import xyz.l7ssha.emr.dto.category.CategoryCreateInputDto
import xyz.l7ssha.emr.dto.category.CategoryPatchInputDto
import xyz.l7ssha.emr.entities.products.Category
import xyz.l7ssha.emr.mapper.CategoryMapper
import xyz.l7ssha.emr.service.entity.CategoryEntityService
import javax.validation.Valid

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    @Autowired val categoryService: CategoryEntityService,
    @Autowired val categoryMapper: CategoryMapper,
    @Autowired val authenticationFacade: AuthenticationFacade
) {
    @GetMapping
    fun getAllAction(specification: RSQLSpecification<Category>, pageable: Pageable) =
        categoryService.findAll(specification.getFiltersAndSpecification(), pageable)
            .map { categoryMapper.categoryToOutputDto(it) }

    @GetMapping("/{id}")
    fun getItemAction(@PathVariable id: Long) =
        categoryService.getById(id).let { categoryMapper.categoryToOutputDto(it) }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_CATEGORIES')")
    @ResponseStatus(HttpStatus.CREATED)
    fun postItemAction(@RequestBody @Valid categoryDto: CategoryCreateInputDto) =
        categoryDto
            .let { categoryMapper.categoryCreateDtoToCategory(categoryDto) }
            .let { categoryService.save(it) }
            .let { categoryMapper.categoryToOutputDto(it) }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('CREATE_CATEGORIES')")
    fun patchItemAction(@PathVariable id: Long, @RequestBody @Valid categoryDto: CategoryPatchInputDto) =
        categoryDto
            .let { categoryMapper.updateCategoryFromPatchDto(categoryService.getById(id), categoryDto) }
            .let { categoryService.save(it) }
            .let { categoryMapper.categoryToOutputDto(it) }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('REMOVE_CATEGORIES')")
    fun removeItemAction(@PathVariable id: Long) =
        categoryService.delete(categoryService.getById(id), authenticationFacade.loggedInUser)
}
