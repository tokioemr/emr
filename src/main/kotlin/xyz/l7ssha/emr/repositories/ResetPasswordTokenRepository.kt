package xyz.l7ssha.emr.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.ResetPasswordToken
import xyz.l7ssha.emr.entities.User
import java.util.*

@Repository
interface ResetPasswordTokenRepository : CrudRepository<ResetPasswordToken, Long> {
    fun findByToken(token: String): Optional<ResetPasswordToken>
    fun findByUser(user: User): Optional<ResetPasswordToken>
}
