package xyz.l7ssha.emr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EmrApplication

fun main(args: Array<String>) {
    runApplication<EmrApplication>(*args)
}
