package xyz.l7ssha.emr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@SpringBootApplication
@EnableJdbcRepositories
class EmrApplication

fun main(args: Array<String>) {
    runApplication<EmrApplication>(*args)
}
