package xyz.l7ssha.emr.repositories

import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.products.Category

@Repository
interface CategoryRepository : SoftDeletableRepository<Category>
