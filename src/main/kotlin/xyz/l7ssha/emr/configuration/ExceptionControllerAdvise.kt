package xyz.l7ssha.emr.configuration

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import xyz.l7ssha.emr.configuration.exception.JwtException
import xyz.l7ssha.emr.dto.exception.ExceptionOutputDto

@ControllerAdvice
class ExceptionControllerAdvise {
    @ExceptionHandler(JwtException::class)
    fun handle(jwtException: JwtException): ResponseEntity<ExceptionOutputDto> {
        return ResponseEntity(
            ExceptionOutputDto(HttpStatus.BAD_REQUEST.value(), jwtException.message ?: ""),
            HttpStatus.BAD_REQUEST
        )
    }
}
