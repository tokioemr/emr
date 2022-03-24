package xyz.l7ssha.emr.repositories

import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.products.Product
import xyz.l7ssha.emr.entities.products.feature.FeatureValue

@Repository
interface ProductRepository : EmrRepository<Product> {
    fun getAllByFeaturesValuesContains(featureValue: FeatureValue): List<Product>
}
