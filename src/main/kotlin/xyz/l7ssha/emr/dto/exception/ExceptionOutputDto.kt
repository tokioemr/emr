package xyz.l7ssha.emr.dto.exception

import java.time.Instant
import java.util.*

data class ExceptionOutputDto(val status: Int, val message: String, val timestamp: Date = Date.from(Instant.now()))
