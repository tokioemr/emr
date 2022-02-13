package xyz.l7ssha.emr.dto.security

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class ForgotPasswordInputDto {
    @NotNull
    @NotEmpty
    @Email
    lateinit var email: String
}
