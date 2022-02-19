package xyz.l7ssha.emr.dto.user

import xyz.l7ssha.emr.validation.NullOrNotBlank
import xyz.l7ssha.emr.validation.ValidPassword
import java.util.*
import javax.validation.constraints.Email

class UserPatchInputDto {
    var email: Optional<@Email @NullOrNotBlank String> = Optional.empty()
    var password: Optional<@ValidPassword @NullOrNotBlank String> = Optional.empty()
}
