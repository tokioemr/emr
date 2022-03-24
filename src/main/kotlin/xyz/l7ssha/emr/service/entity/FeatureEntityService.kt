package xyz.l7ssha.emr.service.entity

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.l7ssha.emr.configuration.exception.PostValidationException
import xyz.l7ssha.emr.entities.products.feature.Feature
import xyz.l7ssha.emr.entities.user.User
import xyz.l7ssha.emr.repositories.FeatureValueRepository

@Service
class FeatureEntityService : EntityService<Feature>() {
    @Autowired
    lateinit var featureValueRepository: FeatureValueRepository

    override fun delete(entity: Feature, user: User) {
        if (featureValueRepository.getAllByFeature(entity).isNotEmpty()) {
            throw PostValidationException("Cannot delete feature since it has assigned feature_values")
        }

        super.delete(entity, user)
    }
}
