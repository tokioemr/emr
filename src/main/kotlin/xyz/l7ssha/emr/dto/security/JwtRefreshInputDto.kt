package xyz.l7ssha.emr.dto.security

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class JwtRefreshInputDto {
    @NotNull
    @NotEmpty
    lateinit var refreshToken: String

    @NotNull
    @NotEmpty
    @Email
    lateinit var email: String;
}
