package xyz.l7ssha.emr.repositories.auth

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.user.ResetPasswordToken
import xyz.l7ssha.emr.entities.user.User
import java.util.*

@Repository
interface ResetPasswordTokenRepository : CrudRepository<ResetPasswordToken, Long> {
    fun findByToken(token: String): Optional<ResetPasswordToken>
    fun findByUser(user: User): Optional<ResetPasswordToken>
}
