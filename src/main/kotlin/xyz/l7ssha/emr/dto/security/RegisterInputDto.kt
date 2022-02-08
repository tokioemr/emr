package xyz.l7ssha.emr.dto.security

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class RegisterInputDto {
    @NotNull
    @NotEmpty
    lateinit var email: String

    @NotNull
    @NotEmpty
    lateinit var password: String
}
