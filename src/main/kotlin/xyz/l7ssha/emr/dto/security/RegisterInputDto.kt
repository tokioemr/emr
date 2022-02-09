package xyz.l7ssha.emr.dto.security

import xyz.l7ssha.emr.validation.ValidPassword
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class RegisterInputDto {
    @NotNull
    @NotEmpty
    @Email
    lateinit var email: String

    @NotNull
    @NotEmpty
    @ValidPassword
    lateinit var password: String
}
