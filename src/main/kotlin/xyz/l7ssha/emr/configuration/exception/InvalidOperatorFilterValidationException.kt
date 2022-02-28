package xyz.l7ssha.emr.configuration.exception

import xyz.l7ssha.emr.dto.pagination.FilteringOperator

class InvalidOperatorFilterValidationException(op: FilteringOperator, fieldName: String) :
    CatchableApplicationException("Operator '$op' cannot be used on field '$fieldName'")
