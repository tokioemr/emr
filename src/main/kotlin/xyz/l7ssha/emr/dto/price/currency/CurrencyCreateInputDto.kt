package xyz.l7ssha.emr.dto.price.currency

import javax.validation.constraints.NotBlank

class CurrencyCreateInputDto {
    @NotBlank
    lateinit var name: String

    @NotBlank
    lateinit var symbol: String

    @NotBlank
    lateinit var alternativeSymbol: String
}
