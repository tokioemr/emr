package xyz.l7ssha.emr.dto.price

import xyz.l7ssha.emr.dto.price.currency.CurrencyOutputDto

class PriceOutputDto(val id: Long, val value: Double, val currency: CurrencyOutputDto)
