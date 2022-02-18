package xyz.l7ssha.emr.dto.user

import javax.validation.constraints.Email

class UserPatchInputDto {
    @Email
    var email: String? = null
}
