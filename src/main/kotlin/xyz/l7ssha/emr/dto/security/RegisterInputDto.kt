package xyz.l7ssha.emr.dto.security

import xyz.l7ssha.emr.validation.ValidPassword
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class RegisterInputDto {
    @NotBlank
    @Email
    lateinit var email: String

    @NotBlank
    @ValidPassword
    lateinit var password: String
}
