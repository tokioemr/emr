package xyz.l7ssha.emr.dto.category

import com.fasterxml.jackson.annotation.JsonInclude
import org.openapitools.jackson.nullable.JsonNullable
import xyz.l7ssha.emr.validation.NullOrNotBlank

@JsonInclude(JsonInclude.Include.NON_NULL)
class CategoryPatchInputDto {
    var name: JsonNullable<@NullOrNotBlank String> = JsonNullable.undefined()
    var parent: JsonNullable<Long> = JsonNullable.undefined()
}
