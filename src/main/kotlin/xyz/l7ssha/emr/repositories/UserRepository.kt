package xyz.l7ssha.emr.repositories

import org.springframework.stereotype.Repository
import xyz.l7ssha.emr.entities.user.User

@Repository
interface UserRepository : EmrRepository<User>
