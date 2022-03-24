package xyz.l7ssha.emr.mapper

import org.springframework.stereotype.Component
import xyz.l7ssha.emr.dto.product.feature.FeatureOutputDto
import xyz.l7ssha.emr.entities.products.feature.Feature

@Component
class FeatureMapper {
    fun featureToOutputDto(feature: Feature) = FeatureOutputDto(
        feature.id,
        feature.name,
        feature.description
    )
}
