package xyz.l7ssha.emr.repositories.auth

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.user.RefreshToken
import xyz.l7ssha.emr.entities.user.User
import java.util.*

@Repository
interface RefreshTokenRepository : CrudRepository<RefreshToken, Long> {
    fun getByRefreshToken(token: String): Optional<RefreshToken>
    fun getByUser(user: User): Optional<RefreshToken>
}
