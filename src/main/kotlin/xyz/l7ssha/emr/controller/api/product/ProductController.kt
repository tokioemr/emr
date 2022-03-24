package xyz.l7ssha.emr.controller.api.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import xyz.l7ssha.emr.dto.RSQLSpecification
import xyz.l7ssha.emr.entities.products.Product
import xyz.l7ssha.emr.mapper.ProductMapper
import xyz.l7ssha.emr.service.entity.ProductEntityService

@RestController
@RequestMapping("/api/products")
class ProductController(
    @Autowired val productMapper: ProductMapper,
    @Autowired val productService: ProductEntityService
) {
    @GetMapping
    fun getAllAction(specification: RSQLSpecification<Product>, pageable: Pageable) =
        productService.findAll(specification.getFiltersAndSpecification(), pageable)
            .map { productMapper.productToOutputDto(it) }

    @GetMapping("/{id}")
    fun getItemAction(@PathVariable id: Long) =
        productService.getById(id).let { productMapper.productToOutputDto(it) }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PRODUCTS')")
    fun postItemAction(): Nothing = TODO()

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('CREATE_PRODUCTS')")
    @Suppress("UnusedPrivateMember")
    fun patchItemAction(@PathVariable id: Long): Nothing = TODO()

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('REMOVE_PRODUCTS')")
    @Suppress("UnusedPrivateMember")
    fun removeItemAction(@PathVariable id: Long): Nothing = TODO()
}
