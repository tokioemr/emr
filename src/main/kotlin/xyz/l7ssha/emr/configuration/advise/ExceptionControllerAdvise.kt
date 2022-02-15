package xyz.l7ssha.emr.configuration.advise

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationException
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationExceptionWithData
import xyz.l7ssha.emr.dto.exception.ExceptionOutputDto
import xyz.l7ssha.emr.dto.exception.ExceptionWithDataOutputDto

@ControllerAdvice
class ExceptionControllerAdvise {
    @ExceptionHandler(CatchableApplicationException::class)
    fun handle(catchableApplicationException: CatchableApplicationException): ResponseEntity<ExceptionOutputDto> {
        return ResponseEntity(
            ExceptionOutputDto(HttpStatus.BAD_REQUEST.value(), catchableApplicationException.message ?: ""),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(CatchableApplicationExceptionWithData::class)
    fun handle(validationExceptionWithData: CatchableApplicationExceptionWithData): ResponseEntity<ExceptionWithDataOutputDto> {
        return ResponseEntity(
            ExceptionWithDataOutputDto(HttpStatus.BAD_REQUEST.value(), validationExceptionWithData.message ?: "", validationExceptionWithData.data),
            HttpStatus.BAD_REQUEST
        )
    }
}
