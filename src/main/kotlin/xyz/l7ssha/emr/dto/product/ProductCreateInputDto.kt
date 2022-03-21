package xyz.l7ssha.emr.dto.product

import org.hibernate.validator.constraints.Length
import xyz.l7ssha.emr.entities.products.PRODUCT_DESCRIPTION_MAX_LENGTH
import xyz.l7ssha.emr.entities.products.ProductCondition
import xyz.l7ssha.emr.validation.ValueOfEnum
import javax.validation.constraints.NotBlank
import kotlin.properties.Delegates

class ProductCreateInputDto {
    @NotBlank
    @Length(min = 3, max = 30)
    lateinit var name: String

    @NotBlank
    @Length(min = 80, max = PRODUCT_DESCRIPTION_MAX_LENGTH)
    lateinit var description: String

    @NotBlank
    @Length(max = 30)
    lateinit var sku: String

    var stock: Long = 0

    @NotBlank
    @Length(max = 30)
    lateinit var producer: String

    @NotBlank
    @Length(max = 30)
    lateinit var model: String

    @NotBlank
    @Length(max = 30)
    lateinit var producerProductNumber: String

    @ValueOfEnum(ProductCondition::class)
    lateinit var condition: ProductCondition

    @NotBlank
    @Length(min = 13, max = 15)
    lateinit var ean: String

    var tags: List<@NotBlank String> = listOf()

    var category by Delegates.notNull<Long>()
}
