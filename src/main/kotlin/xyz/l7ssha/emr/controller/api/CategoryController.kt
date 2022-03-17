package xyz.l7ssha.emr.controller.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import xyz.l7ssha.emr.configuration.security.AuthenticationFacade
import xyz.l7ssha.emr.dto.RSQLSpecification
import xyz.l7ssha.emr.dto.category.CategoryCreateInputDto
import xyz.l7ssha.emr.entities.products.Category
import xyz.l7ssha.emr.mapper.CategoryMapper
import xyz.l7ssha.emr.repositories.CategoryRepository
import javax.validation.Valid

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    @Autowired val categoryRepository: CategoryRepository,
    @Autowired val categoryMapper: CategoryMapper,
    @Autowired val authenticationFacade: AuthenticationFacade
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
    @ResponseStatus(HttpStatus.CREATED)
    fun postItemAction(@RequestBody @Valid categoryDto: CategoryCreateInputDto) =
        categoryDto
            .let { categoryMapper.categoryCreateDtoToCategory(categoryDto) }
            .let { categoryRepository.save(it) }
            .let { categoryMapper.categoryToOutputDto(it) }

    @PatchMapping
    @PreAuthorize("hasAuthority('CREATE_CATEGORIES')")
    fun patchItemAction(): Nothing = TODO()

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('REMOVE_CATEGORIES')")
    fun removeItemAction(@PathVariable id: Long) =
        categoryRepository.softDelete(categoryRepository.getById(id), authenticationFacade.loggedInUser)
}
