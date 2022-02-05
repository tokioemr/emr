package xyz.l7ssha.emr.dto.security

import javax.validation.constraints.NotNull

class JwtRequestInputDto {
    @NotNull
    lateinit var username: String

    @NotNull
    lateinit var password: String
}
