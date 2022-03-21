package xyz.l7ssha.emr.service.entity

import org.springframework.stereotype.Service
import xyz.l7ssha.emr.entities.products.Product

@Service
class ProductEntityService : SoftDeletableEntityService<Product>()
