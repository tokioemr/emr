package xyz.l7ssha.emr.dto.user

import xyz.l7ssha.emr.validation.NullOrNotBlank
import javax.validation.constraints.Email

class UserPatchInputDto {
    @Email
    @NullOrNotBlank
    var email: String? = null
}
