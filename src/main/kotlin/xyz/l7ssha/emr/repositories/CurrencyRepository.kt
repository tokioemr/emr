package xyz.l7ssha.emr.repositories

import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.products.Currency

@Repository
interface CurrencyRepository : EmrRepository<Currency>
