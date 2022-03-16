package xyz.l7ssha.emr.dto.category

import javax.validation.constraints.NotBlank

class CategoryCreateInputDto {
    @NotBlank
    lateinit var name: String

    var parent: Long? = null
    var assignable: Boolean = false
}
