package xyz.l7ssha.emr.mapper

import org.springframework.stereotype.Component
import xyz.l7ssha.emr.dto.price.CurrencyOutputDto
import xyz.l7ssha.emr.dto.price.PriceOutputDto
import xyz.l7ssha.emr.entities.products.Currency
import xyz.l7ssha.emr.entities.products.Price

@Component
class PriceMapper {
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
}
