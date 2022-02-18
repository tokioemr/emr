package xyz.l7ssha.emr.dto.exception

import java.time.Instant
import java.util.*

data class ExceptionWithDataOutputDto(val status: Int, val message: String, val data: Map<String, String>, val timestamp: Date = Date.from(Instant.now()))
