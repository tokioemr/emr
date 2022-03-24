package xyz.l7ssha.emr.repositories

import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.products.feature.Feature
import xyz.l7ssha.emr.entities.products.feature.FeatureValue

@Repository
interface FeatureValueRepository : EmrRepository<FeatureValue> {
    fun getAllByFeature(feature: Feature): List<FeatureValue>
}
