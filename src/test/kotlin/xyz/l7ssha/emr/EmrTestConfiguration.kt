package xyz.l7ssha.emr

import org.mockito.Mockito
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.mail.MailSender
import org.springframework.mail.javamail.JavaMailSender

@TestConfiguration
class EmrTestConfiguration {
    @Bean
    fun javaMailSender(): MailSender {
        return Mockito.mock(JavaMailSender::class.java)
    }
}
