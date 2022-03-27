package xyz.l7ssha.emr.dto.product.feature

import javax.validation.constraints.NotBlank

class FeatureGroupCreateInputDto {
    @NotBlank
    lateinit var name: String

    @NotBlank
    lateinit var description: String

    var features: List<Long> = mutableListOf()
}
