package xyz.l7ssha.emr.dto.security

import xyz.l7ssha.emr.validation.ValidPassword
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class RegisterInputDto {
    @NotNull
    @NotBlank
    @Email
    lateinit var email: String

    @NotNull
    @NotBlank
    @ValidPassword
    lateinit var password: String
}