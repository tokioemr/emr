package xyz.l7ssha.emr.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.security.AuthenticationFacade
import xyz.l7ssha.emr.dto.product.feature.FeatureGroupCreateInputDto
import xyz.l7ssha.emr.dto.product.feature.FeatureGroupOutputDto
import xyz.l7ssha.emr.entities.products.feature.FeatureGroup

@Component
class FeatureGroupMapper(
    @Autowired val authenticationFacade: AuthenticationFacade,
    @Autowired val featureMapper: FeatureMapper
) {
    fun featureGroupToOutputDto(featureGroup: FeatureGroup) = FeatureGroupOutputDto(
        featureGroup.id,
        featureGroup.name,
        featureGroup.description,
        featureGroup.features.map { featureMapper.featureToOutputDto(it) }
    )

    fun featureGroupCreateInputDtoToFeatureGroup(inputDto: FeatureGroupCreateInputDto) =
        FeatureGroup(
            0L,
            inputDto.name,
            inputDto.description,
            listOf() // TODO: Find features based on given array
        ).apply {
            createdBy = authenticationFacade.loggedInUser
        }
}
