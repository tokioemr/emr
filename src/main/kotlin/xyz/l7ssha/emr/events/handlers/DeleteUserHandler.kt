package xyz.l7ssha.emr.events.handlers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.security.AuthenticationFacade
import xyz.l7ssha.emr.events.commands.DeleteUserCommand
import xyz.l7ssha.emr.service.entity.UserEntityService

@Component
class DeleteUserHandler(
    @Autowired val userService: UserEntityService,
    @Autowired val passwordEncoder: PasswordEncoder,
    @Autowired val authenticationFacade: AuthenticationFacade
) {
    @EventListener
    fun handle(command: DeleteUserCommand) {
        val user = userService.getById(command.userId)

        user.email = "anonymized@example.com"
        user.enabled = false
        user.password = passwordEncoder.encode("anonymized")
        user.permissions.toMutableList().clear()

        userService.delete(user, authenticationFacade.loggedInUser)
    }
}
