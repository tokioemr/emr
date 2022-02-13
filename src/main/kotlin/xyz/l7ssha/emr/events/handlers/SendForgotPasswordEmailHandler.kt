package xyz.l7ssha.emr.events.handlers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.exception.ValidationException
import xyz.l7ssha.emr.entities.ForgotPasswordToken
import xyz.l7ssha.emr.events.commands.SendForgotPasswordEmailCommand
import xyz.l7ssha.emr.repositories.ForgotPasswordTokenRepository
import xyz.l7ssha.emr.repositories.UserRepository
import xyz.l7ssha.emr.service.MailService
import java.time.Instant
import java.util.*

@Component
class SendForgotPasswordEmailHandler(
    @Autowired val userRepository: UserRepository,
    @Autowired val mailService: MailService,
    @Autowired val forgotPasswordTokenRepository: ForgotPasswordTokenRepository,
    @Value("\${app.forgotPasswordExpirationMs}") val forgotPasswordExpirationMs: Long
) {
    @EventListener
    fun on(event: SendForgotPasswordEmailCommand) {
        val user = userRepository.findByEmail(event.email).orElseThrow {
            throw ValidationException("Account with given email does not exists")
        }

        val forgotPasswordToken = ForgotPasswordToken(0L, user, UUID.randomUUID().toString(), Instant.now().plusMillis(forgotPasswordExpirationMs))
        forgotPasswordTokenRepository.save(forgotPasswordToken)

        mailService.sendForgotPasswordEmail(user.email, forgotPasswordToken.token)
    }
}
