package xyz.l7ssha.emr.dto.exception

data class ExceptionWithDataOutputDto(val statusCode: Int, val message: String, val data: Map<String, String>)
