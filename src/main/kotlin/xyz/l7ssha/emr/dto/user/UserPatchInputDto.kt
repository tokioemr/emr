package xyz.l7ssha.emr.dto.user

import xyz.l7ssha.emr.validation.NullOrNotBlank
import java.util.*
import javax.validation.constraints.Email

class UserPatchInputDto {
    var email: Optional<@Email @NullOrNotBlank String> = Optional.empty()
}
