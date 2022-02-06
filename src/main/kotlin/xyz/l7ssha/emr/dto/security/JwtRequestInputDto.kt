package xyz.l7ssha.emr.dto.security

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class JwtRequestInputDto {
    @NotNull
    @NotEmpty
    lateinit var username: String

    @NotNull
    @NotEmpty
    lateinit var password: String
}
