package xyz.l7ssha.emr.repositories

import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.products.Currency
import xyz.l7ssha.emr.entities.products.Price

@Repository
interface PriceRepository : EmrRepository<Price> {
    fun countAllByCurrency(currency: Currency): Int
}
