package xyz.l7ssha.emr.repositories

import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.products.Image
import xyz.l7ssha.emr.entities.products.Product
import xyz.l7ssha.emr.entities.products.feature.FeatureValue

@Repository
interface ProductRepository : EmrRepository<Product> {
    fun countAllByFeaturesValuesContains(featureValue: FeatureValue): Int
    fun countAllByImagesContains(image: Image): Int
}
