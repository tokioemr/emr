package xyz.l7ssha.emr

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.mail.MailSender
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import xyz.l7ssha.emr.configuration.security.UserPrincipal

@TestConfiguration
class TestConfiguration {
    @Bean
    fun javaMailSender(): MailSender {
        return Mockito.mock(JavaMailSender::class.java)
    }

    @Primary
    @Bean
    fun userDetailsService(): UserDetailsService {
        val adminUser = UserPrincipal(0L, "admin@example.com", "test", emptyList(), true)
        return InMemoryUserDetailsManager(listOf(adminUser))
    }
}

@SpringBootTest
class EmrApplicationTests {
    @Test
    fun contextLoads() { }
}
