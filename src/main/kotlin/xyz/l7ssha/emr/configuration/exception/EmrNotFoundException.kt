package xyz.l7ssha.emr.configuration.exception

import org.springframework.http.HttpStatus

class EmrNotFoundException(message: String) : CatchableApplicationException(message, HttpStatus.NOT_FOUND)
