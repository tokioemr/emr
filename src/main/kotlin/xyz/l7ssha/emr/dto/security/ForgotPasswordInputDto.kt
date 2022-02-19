package xyz.l7ssha.emr.dto.security

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class ForgotPasswordInputDto {
    @NotBlank
    @Email
    lateinit var email: String
}
