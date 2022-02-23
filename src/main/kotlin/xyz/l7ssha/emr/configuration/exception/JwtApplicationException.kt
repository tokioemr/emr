package xyz.l7ssha.emr.configuration.exception

import org.springframework.http.HttpStatus

class JwtApplicationException(message: String) : CatchableApplicationException(message, HttpStatus.UNAUTHORIZED)
