package xyz.l7ssha.emr.service.entity

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.l7ssha.emr.configuration.exception.PostValidationException
import xyz.l7ssha.emr.entities.products.feature.FeatureValue
import xyz.l7ssha.emr.entities.user.User
import xyz.l7ssha.emr.repositories.ProductRepository

@Service
class FeatureValueEntityService : EntityService<FeatureValue>() {
    @Autowired
    lateinit var productRepository: ProductRepository

    override fun delete(entity: FeatureValue, user: User) {
        if (productRepository.getAllByFeaturesValuesContains(entity).isNotEmpty()) {
            throw PostValidationException("Cannot delete feature_groups since it has assigned features")
        }

        super.delete(entity, user)
    }
}
