package xyz.l7ssha.emr.mapper

import org.springframework.stereotype.Component
import xyz.l7ssha.emr.dto.product.ProductOutputDto
import xyz.l7ssha.emr.entities.products.Product

@Component
class ProductMapper {
    fun productToOutputDto(product: Product) = ProductOutputDto(product.id, product.name)
}
