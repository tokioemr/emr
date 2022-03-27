package xyz.l7ssha.emr.dto.product.feature

import com.fasterxml.jackson.annotation.JsonInclude
import org.openapitools.jackson.nullable.JsonNullable
import javax.validation.constraints.NotBlank

@JsonInclude(JsonInclude.Include.NON_NULL)
class FeatureGroupPatchInputDto {
    var name: JsonNullable<@NotBlank String> = JsonNullable.undefined()
    var description: JsonNullable<@NotBlank String> = JsonNullable.undefined()
    var features: JsonNullable<List<Long>> = JsonNullable.undefined()
}
