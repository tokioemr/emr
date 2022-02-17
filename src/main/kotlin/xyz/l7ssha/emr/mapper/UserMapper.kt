package xyz.l7ssha.emr.mapper

import org.springframework.stereotype.Component
import xyz.l7ssha.emr.dto.user.UserOutputDto
import xyz.l7ssha.emr.entities.User

@Component
class UserMapper {
    fun userToUserOutputDto(user: User) = UserOutputDto(
        user.id,
        user.email,
        user.enabled,
        user.permissions.map { it.name.name }
    )
}
