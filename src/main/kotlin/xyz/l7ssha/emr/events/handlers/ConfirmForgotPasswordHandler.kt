package xyz.l7ssha.emr.events.handlers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.exception.ValidationException
import xyz.l7ssha.emr.events.commands.ConfirmForgotPasswordCommand
import xyz.l7ssha.emr.repositories.ResetPasswordTokenRepository
import xyz.l7ssha.emr.repositories.UserRepository
import java.time.Instant

@Component
class ConfirmForgotPasswordHandler(
    @Autowired val userRepository: UserRepository,
    @Autowired val resetPasswordTokenRepository: ResetPasswordTokenRepository,
    @Autowired val passwordEncoder: PasswordEncoder
){
    @EventListener
    fun on(event: ConfirmForgotPasswordCommand) {
        val user = userRepository.findByEmail(event.email).orElseThrow {
            ValidationException("Account with given email does not exists")
        }

        val token = resetPasswordTokenRepository.findByToken(event.token).orElseThrow {
            ValidationException("Given token does not exists")
        }

        if (token.expirationDate.isBefore(Instant.now())) {
            throw ValidationException("Given token expired")
        }

        user.password = passwordEncoder.encode(event.password)
        userRepository.save(user)

        resetPasswordTokenRepository.delete(token)
    }
}
