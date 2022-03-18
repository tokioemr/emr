package xyz.l7ssha.emr.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.security.PermissionType
import xyz.l7ssha.emr.dto.user.UserCreateInputDto
import xyz.l7ssha.emr.dto.user.UserOutputDto
import xyz.l7ssha.emr.dto.user.UserPatchInputDto
import xyz.l7ssha.emr.entities.user.User
import xyz.l7ssha.emr.entities.user.UserPermission

@Component
class UserMapper(@Autowired val passwordEncoder: PasswordEncoder) {
    fun userToUserOutputDto(user: User) = UserOutputDto(
        user.id,
        user.email,
        user.enabled,
        user.permissions.map { it.name.name }
    )

    fun updateUserFromPatchDto(user: User, patchDto: UserPatchInputDto): User {
        return user.apply {
            patchDto.email.ifPresent {
                this.email = it
            }

            patchDto.password.ifPresent {
                this.password = passwordEncoder.encode(it)
            }
        }
    }

    fun createUserFromDto(createDto: UserCreateInputDto): User =
        User(
            0L,
            createDto.email,
            passwordEncoder.encode(createDto.password),
            createDto.enabled,
            createDto.permissions.map { mapStringToUserPermission(it) }.toSet()
        )

    private fun mapStringToUserPermission(permission: String): UserPermission =
        UserPermission(0L, PermissionType.valueOf(permission))
}
