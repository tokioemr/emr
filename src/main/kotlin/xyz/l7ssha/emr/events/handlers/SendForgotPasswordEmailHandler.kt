package xyz.l7ssha.emr.events.handlers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.exception.ValidationException
import xyz.l7ssha.emr.events.commands.SendForgotPasswordEmailCommand
import xyz.l7ssha.emr.repositories.UserRepository
import xyz.l7ssha.emr.service.MailService
import xyz.l7ssha.emr.service.ResetPasswordTokenService

@Component
class SendForgotPasswordEmailHandler(
    @Autowired val userRepository: UserRepository,
    @Autowired val mailService: MailService,
    @Autowired val resetPasswordTokenService: ResetPasswordTokenService
) {
    @EventListener
    fun on(event: SendForgotPasswordEmailCommand) {
        val user = userRepository.findByEmail(event.email).orElseThrow {
            ValidationException("Account with given email does not exists")
        }

        val resetPasswordToken = resetPasswordTokenService.generateResetPasswordToken(user)

        mailService.sendForgotPasswordEmail(user.email, resetPasswordToken.token)
    }
}
