package xyz.l7ssha.emr.events.handlers.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.events.commands.auth.SendEmailCommand

@Component
class SendEmailHandler(
    @Autowired val mailSender: MailSender
) {
    @EventListener
    @Async
    fun on(event: SendEmailCommand) {
        val message = SimpleMailMessage().apply {
            setFrom("noreply@l7ssha.xyz")
            setTo(event.email)
            setSubject(event.subject)
            setText(event.body)
        }

        mailSender.send(message)
    }
}
