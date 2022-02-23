package xyz.l7ssha.emr.configuration.advise

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationException
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationExceptionWithData
import xyz.l7ssha.emr.dto.exception.ExceptionOutputDto
import xyz.l7ssha.emr.dto.exception.ExceptionWithDataOutputDto
import javax.persistence.EntityNotFoundException

@ControllerAdvice
class ExceptionControllerAdvise {
    @ExceptionHandler(CatchableApplicationException::class)
    fun handle(catchableApplicationException: CatchableApplicationException): ResponseEntity<ExceptionOutputDto> {
        return ResponseEntity(
            ExceptionOutputDto(
                catchableApplicationException.status.value(),
                catchableApplicationException.message ?: ""
            ),
            catchableApplicationException.status
        )
    }

    @ExceptionHandler(CatchableApplicationExceptionWithData::class)
    fun handle(
        validationExceptionWithData: CatchableApplicationExceptionWithData
    ): ResponseEntity<ExceptionWithDataOutputDto> {
        return ResponseEntity(
            ExceptionWithDataOutputDto(
                validationExceptionWithData.status.value(),
                validationExceptionWithData.message ?: "", validationExceptionWithData.data
            ),
            validationExceptionWithData.status
        )
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handle(): ResponseEntity.HeadersBuilder<*> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
    }
}
