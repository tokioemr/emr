package xyz.l7ssha.emr.configuration.exception

class CatchableApplicationExceptionWithData(message: String, val data: Map<String, String>): CatchableApplicationException(message)
