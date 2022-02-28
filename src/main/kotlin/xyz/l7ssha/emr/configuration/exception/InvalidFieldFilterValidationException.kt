package xyz.l7ssha.emr.configuration.exception

class InvalidFieldFilterValidationException(fieldName: String) :
    CatchableApplicationException("Filter with name: '$fieldName' does not exist")
