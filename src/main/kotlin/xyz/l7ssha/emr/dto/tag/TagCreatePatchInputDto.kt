package xyz.l7ssha.emr.dto.tag

import javax.validation.constraints.NotBlank

class TagCreatePatchInputDto {
    @NotBlank
    lateinit var name: String
}
