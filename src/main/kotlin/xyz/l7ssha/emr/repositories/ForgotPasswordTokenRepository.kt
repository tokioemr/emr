package xyz.l7ssha.emr.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.ForgotPasswordToken

@Repository
interface ForgotPasswordTokenRepository : CrudRepository<ForgotPasswordToken, Long> {
}
