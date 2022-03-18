package xyz.l7ssha.emr

import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.mail.MailSender
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.entities.products.Category
import xyz.l7ssha.emr.entities.user.User
import xyz.l7ssha.emr.service.entity.CategoryEntityService
import xyz.l7ssha.emr.service.entity.UserEntityService
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
    private lateinit var userService: UserEntityService

    @Autowired
    private lateinit var categoryService: CategoryEntityService

    @PostConstruct
    fun init() {
        userService.save(
            User(
                0L,
                "user",
                "",
                true,
                setOf(),
                false
            )
        )

        categoryService.save(
            Category(
                1337L,
                "test1"
            )
        )

        categoryService.save(
            Category(
                1338L,
                "test2"
            )
        )
    }
}
