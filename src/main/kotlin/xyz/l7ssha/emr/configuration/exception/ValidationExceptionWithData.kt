package xyz.l7ssha.emr.configuration.exception

class ValidationExceptionWithData(message: String, val data: Map<String, String>): ValidationException(message)
