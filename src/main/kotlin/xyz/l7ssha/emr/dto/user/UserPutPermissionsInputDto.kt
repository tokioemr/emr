package xyz.l7ssha.emr.dto.user

import javax.validation.constraints.NotBlank

class UserPutPermissionsInputDto {
    var permissions: Set<@NotBlank String> = setOf()
}
