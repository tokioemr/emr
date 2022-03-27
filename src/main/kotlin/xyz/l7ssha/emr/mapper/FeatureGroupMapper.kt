package xyz.l7ssha.emr.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.security.AuthenticationFacade
import xyz.l7ssha.emr.dto.product.feature.FeatureGroupCreateInputDto
import xyz.l7ssha.emr.dto.product.feature.FeatureGroupOutputDto
import xyz.l7ssha.emr.dto.product.feature.FeatureGroupPatchInputDto
import xyz.l7ssha.emr.entities.products.feature.FeatureGroup
import xyz.l7ssha.emr.service.entity.FeatureEntityService

@Component
class FeatureGroupMapper(
    @Autowired val authenticationFacade: AuthenticationFacade,
    @Autowired val featureMapper: FeatureMapper,
    @Autowired val featureService: FeatureEntityService
) {
    fun featureGroupToOutputDto(featureGroup: FeatureGroup) = FeatureGroupOutputDto(
        featureGroup.id,
        featureGroup.name,
        featureGroup.description,
        featureGroup.features.map { featureMapper.featureToOutputDto(it) }
    )

    fun featureGroupPatchInputDtoToFeatureGroup(
        featureGroup: FeatureGroup,
        inputDto: FeatureGroupPatchInputDto
    ): FeatureGroup {
        return featureGroup.apply {
            inputDto.name.ifPresent {
                this.name = it
            }

            inputDto.description.ifPresent {
                this.description = it
            }

            inputDto.features.ifPresent {
                val inputFeatures = it.map { id -> featureService.getById(id) }

                for (feature in inputFeatures) {
                    if (!this.features.contains(feature)) {
                        (this.features as MutableList).add(feature)
                    }
                }
            }
        }
    }

    fun featureGroupCreateInputDtoToFeatureGroup(featureGroup: FeatureGroup, inputDto: FeatureGroupCreateInputDto) =
        featureGroup.apply {
            this.name = inputDto.name
            this.description = inputDto.description
            (this.features as MutableList).clear()
            (this.features as MutableList).addAll(inputDto.features.map { featureService.getById(it) })
        }

    fun featureGroupCreateInputDtoToFeatureGroup(inputDto: FeatureGroupCreateInputDto) =
        FeatureGroup(
            0L,
            inputDto.name,
            inputDto.description,
            inputDto.features.map { featureService.getById(it) }
        ).apply {
            createdBy = authenticationFacade.loggedInUser
        }
}
