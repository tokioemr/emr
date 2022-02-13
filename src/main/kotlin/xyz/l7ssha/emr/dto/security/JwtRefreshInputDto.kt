package xyz.l7ssha.emr.dto.security

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class JwtRefreshInputDto {
    @NotNull
    @NotBlank
    lateinit var refreshToken: String

    @NotNull
    @NotBlank
    @Email
    lateinit var email: String;
}
