package xyz.l7ssha.emr.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.products.Category

@Repository
interface CategoryRepository : JpaRepository<Category, Long>, JpaSpecificationExecutor<Category>