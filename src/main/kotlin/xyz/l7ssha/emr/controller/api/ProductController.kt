package xyz.l7ssha.emr.controller.api

import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
}
