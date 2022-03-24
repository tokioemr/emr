package xyz.l7ssha.emr.dto.product.feature

data class FeatureGroupOutputDto(
    val id: Long,
    val name: String,
    val description: String,
    val features: List<FeatureOutputDto>
)
