package xyz.l7ssha.emr.events.handlers.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationException
import xyz.l7ssha.emr.events.commands.auth.ConfirmForgotPasswordCommand
import xyz.l7ssha.emr.repositories.auth.ResetPasswordTokenRepository
import xyz.l7ssha.emr.service.entity.UserEntityService
import java.time.Instant

@Component
class ConfirmForgotPasswordHandler(
    @Autowired val userService: UserEntityService,
    @Autowired val resetPasswordTokenRepository: ResetPasswordTokenRepository,
    @Autowired val passwordEncoder: PasswordEncoder
){
    @EventListener
    fun on(event: ConfirmForgotPasswordCommand) {
        val user = userService.findByEmail(event.email).orElseThrow {
            CatchableApplicationException("Account with given email does not exists")
        }

        val token = resetPasswordTokenRepository.findByToken(event.token).orElseThrow {
            CatchableApplicationException("Given token does not exists")
        }

        if (token.expirationDate.isBefore(Instant.now())) {
            throw CatchableApplicationException("Given token expired")
        }

        user.password = passwordEncoder.encode(event.password)
        user.passwordExpired = false
        userService.save(user)

        resetPasswordTokenRepository.delete(token)
    }
}
