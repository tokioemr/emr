package xyz.l7ssha.emr.controller.api

import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import xyz.l7ssha.emr.dto.RSQLSpecification
import xyz.l7ssha.emr.entities.products.Product
import xyz.l7ssha.emr.mapper.ProductMapper
import xyz.l7ssha.emr.repositories.ProductRepository

@RestController
@RequestMapping("/api/products")
class ProductController(val productMapper: ProductMapper, val productRepository: ProductRepository) {

    @GetMapping
    fun getAllAction(specification: RSQLSpecification<Product>, pageable: Pageable) =
        productRepository.findAll(specification.getFiltersAndSpecification(), pageable)
            .map { productMapper.productToOutputDto(it) }

    @GetMapping("/{id}")
    fun getItemAction(@PathVariable id: Long) =
        productRepository.getById(id).let { productMapper.productToOutputDto(it) }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PRODUCTS')")
    fun postItemAction(): Nothing = TODO()

    @PatchMapping
    @PreAuthorize("hasAuthority('CREATE_PRODUCTS')")
    fun patchItemAction(): Nothing = TODO()

    @DeleteMapping
    @PreAuthorize("hasAuthority('REMOVE_PRODUCTS')")
    fun removeItemAction(): Nothing = TODO()
}
