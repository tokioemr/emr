package xyz.l7ssha.emr

import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.mail.MailSender
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.entities.user.User
import xyz.l7ssha.emr.repositories.UserRepository
import javax.annotation.PostConstruct

@TestConfiguration
class EmrTestConfiguration {
    @Bean
    fun javaMailSender(): MailSender {
        return Mockito.mock(JavaMailSender::class.java)
    }
}

@Component
class SqlGenerationBean {
    @Autowired
    private lateinit var repo: UserRepository

    @PostConstruct
    fun init() {
        repo.save(
            User(
                0L,
                "user",
                "",
                true,
                setOf(),
                false
            )
        )
    }
}
