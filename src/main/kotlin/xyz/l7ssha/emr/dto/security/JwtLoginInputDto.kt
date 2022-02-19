package xyz.l7ssha.emr.dto.security

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class JwtLoginInputDto {
    @NotBlank
    @Email
    lateinit var email: String

    @NotBlank
    lateinit var password: String
}
