package xyz.l7ssha.emr.configuration.exception

import org.springframework.http.HttpStatus

open class CatchableApplicationException(
    message: String,
    val status: HttpStatus = HttpStatus.BAD_REQUEST
) : Exception(message)
