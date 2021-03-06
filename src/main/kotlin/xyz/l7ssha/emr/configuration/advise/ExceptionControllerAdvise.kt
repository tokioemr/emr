package xyz.l7ssha.emr.configuration.advise

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationException
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationWithDataException
import xyz.l7ssha.emr.dto.exception.ExceptionOutputDto
import xyz.l7ssha.emr.dto.exception.ExceptionWithDataOutputDto
import xyz.l7ssha.emr.mapper.ExceptionMapper
import javax.persistence.EntityNotFoundException

@ControllerAdvice
@Suppress("UnusedPrivateMember")
class ExceptionControllerAdvise {
    @Autowired
    lateinit var exceptionMapper: ExceptionMapper

    @ExceptionHandler(CatchableApplicationException::class)
    fun handle(exception: CatchableApplicationException): ResponseEntity<ExceptionOutputDto> {
        return ResponseEntity(
            exceptionMapper.catchableExceptionToDto(exception),
            exception.status
        )
    }

    @ExceptionHandler(CatchableApplicationWithDataException::class)
    fun handle(
        exceptionWithData: CatchableApplicationWithDataException
    ): ResponseEntity<ExceptionWithDataOutputDto> {
        return ResponseEntity(
            exceptionMapper.catchableExceptionWithDataToDto(exceptionWithData),
            exceptionWithData.status
        )
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handle(e: EntityNotFoundException): ResponseEntity.HeadersBuilder<*> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
    }
}
