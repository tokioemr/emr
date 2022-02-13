package xyz.l7ssha.emr.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service

@Service
class MailService(
    @Autowired val mailSender: MailSender,
    @Value("\${app.baseUrl}") val baseUrl: String
) {
    fun sendForgotPasswordEmail(email: String, resetToken: String) {
        val message = SimpleMailMessage()
        message.setFrom("noreply@l7ssha.xyz")
        message.setTo(email)
        message.setSubject("EMR password reset")
        message.setText("You requested to reset your password to your EMR account:<br>" +
                "Click here to reset your password: $baseUrl/forgot-password-confirm/$resetToken")
        mailSender.send(message)
    }
}
