package xyz.l7ssha.emr.configuration.exception

import org.springframework.http.HttpStatus

class CatchableApplicationExceptionWithData(
    message: String,
    status: HttpStatus,
    val data: Map<String, String>
) : CatchableApplicationException(message, status)
