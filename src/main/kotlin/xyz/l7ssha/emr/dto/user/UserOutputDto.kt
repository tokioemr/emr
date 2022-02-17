package xyz.l7ssha.emr.dto.user

data class UserOutputDto(val id: Long, val email: String, val enabled: Boolean, val permissions: List<String>)
