package xyz.l7ssha.emr.repositories

import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.user.User
import java.util.*

@Repository
interface UserRepository : SoftDeletableRepository<User> {
    fun findByEmail(email: String): Optional<User>
}
