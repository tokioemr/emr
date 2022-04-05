package xyz.l7ssha.emr.service.entity

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import xyz.l7ssha.emr.configuration.exception.PostValidationException
import xyz.l7ssha.emr.entities.products.Currency
import xyz.l7ssha.emr.entities.user.User
import xyz.l7ssha.emr.repositories.PriceRepository

@Service
class CurrencyEntityService(
    @Autowired val priceRepository: PriceRepository
) : SoftDeletableEntityService<Currency>() {
    override fun delete(entity: Currency, user: User) {
        if (priceRepository.countAllByCurrency(entity) > 0) {
            throw PostValidationException("Cannot delete currency that is used in products")
        }

        super.delete(entity, user)
    }
}
