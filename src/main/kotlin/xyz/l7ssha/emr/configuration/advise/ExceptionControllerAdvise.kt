package xyz.l7ssha.emr.configuration.advise

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import xyz.l7ssha.emr.configuration.exception.ValidationException
import xyz.l7ssha.emr.configuration.exception.ValidationExceptionWithData
import xyz.l7ssha.emr.dto.exception.ExceptionOutputDto
import xyz.l7ssha.emr.dto.exception.ExceptionWithDataOutputDto

@ControllerAdvice
class ExceptionControllerAdvise {
    @ExceptionHandler(ValidationException::class)
    fun handle(validationException: ValidationException): ResponseEntity<ExceptionOutputDto> {
        return ResponseEntity(
            ExceptionOutputDto(HttpStatus.BAD_REQUEST.value(), validationException.message ?: ""),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(ValidationExceptionWithData::class)
    fun handle(validationExceptionWithData: ValidationExceptionWithData): ResponseEntity<ExceptionWithDataOutputDto> {
        return ResponseEntity(
            ExceptionWithDataOutputDto(HttpStatus.BAD_REQUEST.value(), validationExceptionWithData.message ?: "", validationExceptionWithData.data),
            HttpStatus.BAD_REQUEST
        )
    }
}
