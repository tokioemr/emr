package xyz.l7ssha.emr.events.handlers.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationException
import xyz.l7ssha.emr.events.commands.auth.SendEmailCommand
import xyz.l7ssha.emr.events.commands.auth.SendForgotPasswordEmailCommand
import xyz.l7ssha.emr.service.ResetPasswordTokenService
import xyz.l7ssha.emr.service.entity.UserEntityService

@Component
class SendForgotPasswordEmailHandler(
    @Autowired val userService: UserEntityService,
    @Autowired val resetPasswordTokenService: ResetPasswordTokenService,
    @Autowired val eventPublisher: ApplicationEventPublisher,
    @Value("\${app.baseUrl}") val baseUrl: String
) {
    @EventListener
    fun on(event: SendForgotPasswordEmailCommand) {
        val user = userService.findByEmail(event.email).orElseThrow {
            CatchableApplicationException("Account with given email does not exists")
        }

        val resetPasswordToken = resetPasswordTokenService.generateResetPasswordToken(user)

        eventPublisher.publishEvent(
            SendEmailCommand(
                user.email,
                "EMR password reset",
                "You requested to reset your password to your EMR account:<br>" +
                    "Click here to reset your password: $baseUrl/forgot-password-confirm/${resetPasswordToken.token}"
            )
        )
    }
}
