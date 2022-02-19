package xyz.l7ssha.emr.dto.user

import xyz.l7ssha.emr.configuration.security.PermissionType
import xyz.l7ssha.emr.validation.ValidPassword
import xyz.l7ssha.emr.validation.ValueOfEnum
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class UserCreateInputDto {
    @Email
    @NotBlank
    lateinit var email: String

    @ValidPassword
    @NotBlank
    lateinit var password: String

    @NotNull
    var enabled: Boolean = true

    @NotNull
    var permissions: List<@NotBlank @ValueOfEnum(PermissionType::class) String> = emptyList()
}
