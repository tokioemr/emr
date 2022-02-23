package xyz.l7ssha.emr.events.handlers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.events.commands.DeleteUserCommand
import xyz.l7ssha.emr.repositories.UserRepository
import java.time.Instant

@Component
class DeleteUserHandler(
    @Autowired val userRepository: UserRepository,
    @Autowired val passwordEncoder: PasswordEncoder
) {
    @EventListener
    fun handle(command: DeleteUserCommand) {
        val user = userRepository.getById(command.userId)

        user.email = "anonymized@example.com"
        user.enabled = false
        user.deletedAt = Instant.now()
        user.password = passwordEncoder.encode("anonymized")
        user.permissions.toMutableList().clear()

        userRepository.save(user)
    }
}
