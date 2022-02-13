package xyz.l7ssha.emr.dto.security

import xyz.l7ssha.emr.validation.ValidPassword
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class ForgotPasswordConfirmInputDto {
    @NotBlank
    @NotNull
    @Email
    lateinit var email: String

    @NotBlank
    @NotNull
    @ValidPassword
    lateinit var password: String

    @NotBlank
    @NotNull
    lateinit var token: String
}
