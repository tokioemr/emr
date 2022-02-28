package xyz.l7ssha.emr.mapper

import org.springframework.stereotype.Component
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationException
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationExceptionWithData
import xyz.l7ssha.emr.dto.exception.ExceptionOutputDto
import xyz.l7ssha.emr.dto.exception.ExceptionWithDataOutputDto

@Component
class ExceptionMapper {
    fun catchableExceptionToDto(exception: CatchableApplicationException): ExceptionOutputDto {
        return ExceptionOutputDto(
            exception.status.value(),
            exception.message ?: ""
        )
    }

    fun catchableExceptionWithDataToDto(exception: CatchableApplicationExceptionWithData): ExceptionWithDataOutputDto {
        return ExceptionWithDataOutputDto(
            exception.status.value(),
            exception.message ?: "",
            exception.data
        )
    }
}
