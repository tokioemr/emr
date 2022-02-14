package xyz.l7ssha.emr.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import xyz.l7ssha.emr.configuration.exception.ValidationException
import xyz.l7ssha.emr.entities.ResetPasswordToken
import xyz.l7ssha.emr.entities.User
import xyz.l7ssha.emr.repositories.ResetPasswordTokenRepository
import java.time.Instant
import java.util.*

@Service
class ResetPasswordTokenService(
    @Autowired val resetPasswordTokenRepository: ResetPasswordTokenRepository,
    @Value("\${app.forgotPasswordExpirationMs}") val forgotPasswordExpirationMs: Long
) {
    fun generateResetPasswordToken(user: User): ResetPasswordToken {
        val now = Instant.now()

        resetPasswordTokenRepository.findByUser(user).ifPresent {
            if (it.expirationDate.isAfter(now)) {
                throw ValidationException("There is already ongoing request")
            }

            resetPasswordTokenRepository.delete(it)
        }

        val resetPasswordToken = ResetPasswordToken(0L, user, UUID.randomUUID().toString(), now.plusMillis(forgotPasswordExpirationMs))
        resetPasswordTokenRepository.save(resetPasswordToken)

        return resetPasswordToken
    }
}
