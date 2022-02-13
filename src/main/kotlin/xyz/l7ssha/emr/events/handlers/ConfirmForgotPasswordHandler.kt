package xyz.l7ssha.emr.events.handlers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.exception.ValidationException
import xyz.l7ssha.emr.events.commands.ConfirmForgotPasswordCommand
import xyz.l7ssha.emr.repositories.ForgotPasswordTokenRepository
import xyz.l7ssha.emr.repositories.UserRepository
import java.time.Instant
import javax.transaction.Transactional

@Component
class ConfirmForgotPasswordHandler(
    @Autowired val userRepository: UserRepository,
    @Autowired val forgotPasswordTokenRepository: ForgotPasswordTokenRepository,
    @Autowired val passwordEncoder: PasswordEncoder
){
    @EventListener
    @Transactional
    fun on(event: ConfirmForgotPasswordCommand) {
        val user = userRepository.findByEmail(event.email).orElseThrow {
            throw ValidationException("Account with given email does not exists")
        }

        val token = forgotPasswordTokenRepository.findByToken(event.token).orElseThrow {
            throw ValidationException("Given token does not exists")
        }

        if (token.expirationDate < Instant.now()) {
            throw ValidationException("Given token expired")
        }

        user.password = passwordEncoder.encode(event.password)
        userRepository.save(user)

        forgotPasswordTokenRepository.delete(token)
    }
}