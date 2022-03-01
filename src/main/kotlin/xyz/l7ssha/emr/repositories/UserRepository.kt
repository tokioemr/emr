package xyz.l7ssha.emr.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.User
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    fun findByEmail(email: String): Optional<User>
}
