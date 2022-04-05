package xyz.l7ssha.emr.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.security.AuthenticationFacade
import xyz.l7ssha.emr.dto.price.PriceOutputDto
import xyz.l7ssha.emr.dto.price.currency.CurrencyCreateInputDto
import xyz.l7ssha.emr.dto.price.currency.CurrencyOutputDto
import xyz.l7ssha.emr.entities.products.Currency
import xyz.l7ssha.emr.entities.products.Price

@Component
class PriceMapper(
    @Autowired private val authenticationFacade: AuthenticationFacade
) {
    fun priceToOutputDto(price: Price) = PriceOutputDto(
        price.id,
        price.value,
        currencyToOutputDto(price.currency)
    )

    fun currencyToOutputDto(currency: Currency) = CurrencyOutputDto(
        currency.id,
        currency.name,
        currency.symbol,
        currency.alternativeSymbol
    )

    fun inputDtoToCurrency(dto: CurrencyCreateInputDto): Currency =
        Currency(
            0L,
            dto.name,
            dto.symbol,
            dto.alternativeSymbol
        ).apply {
            createdBy = authenticationFacade.loggedInUser
        }
}
