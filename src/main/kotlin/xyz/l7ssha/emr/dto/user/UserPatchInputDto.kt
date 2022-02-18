package xyz.l7ssha.emr.dto.user

import java.util.Optional
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class UserPatchInputDto {
    var email: Optional<@NotBlank @Email String> = Optional.empty()
}
