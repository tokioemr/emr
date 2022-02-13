package xyz.l7ssha.emr.dto.security

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class JwtLoginInputDto {
    @NotNull
    @NotBlank
    lateinit var email: String

    @NotNull
    @NotBlank
    lateinit var password: String
}
