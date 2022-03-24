package xyz.l7ssha.emr.service.entity

import org.springframework.stereotype.Service
import xyz.l7ssha.emr.configuration.exception.PostValidationException
import xyz.l7ssha.emr.entities.products.feature.FeatureGroup
import xyz.l7ssha.emr.entities.user.User

@Service
class FeatureGroupEntityService : EntityService<FeatureGroup>() {
    override fun delete(entity: FeatureGroup, user: User) {
        if (entity.features.isNotEmpty()) {
            throw PostValidationException("Cannot delete feature_groups since it has assigned features")
        }

        super.delete(entity, user)
    }
}
