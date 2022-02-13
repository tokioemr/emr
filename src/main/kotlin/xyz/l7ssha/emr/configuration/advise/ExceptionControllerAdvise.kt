package xyz.l7ssha.emr.configuration.advise

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import xyz.l7ssha.emr.configuration.exception.ValidationException
import xyz.l7ssha.emr.dto.exception.ExceptionOutputDto

@ControllerAdvice
class ExceptionControllerAdvise {
    @ExceptionHandler(ValidationException::class)
    fun handle(jwtException: ValidationException): ResponseEntity<ExceptionOutputDto> {
        return ResponseEntity(
            ExceptionOutputDto(HttpStatus.BAD_REQUEST.value(), jwtException.message ?: ""),
            HttpStatus.BAD_REQUEST
        )
    }
}
