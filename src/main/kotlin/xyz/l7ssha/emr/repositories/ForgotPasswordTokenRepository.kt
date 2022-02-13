package xyz.l7ssha.emr.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.ForgotPasswordToken
import xyz.l7ssha.emr.entities.User
import java.util.*

@Repository
interface ForgotPasswordTokenRepository : CrudRepository<ForgotPasswordToken, Long> {
    fun findByToken(token: String): Optional<ForgotPasswordToken>
    fun findByUser(user: User): Optional<ForgotPasswordToken>
}
