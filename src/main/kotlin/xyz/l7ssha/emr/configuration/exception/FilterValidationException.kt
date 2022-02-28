package xyz.l7ssha.emr.configuration.exception

class FilterValidationException(private val fieldName: String) :
    CatchableApplicationException("Filter with name: '$fieldName' does not exist")
