package xyz.l7ssha.emr.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import xyz.l7ssha.emr.configuration.security.UserPrincipal
import xyz.l7ssha.emr.repositories.UserRepository
import javax.transaction.Transactional

@Service
@Transactional
class UserDetailsService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
       val user = userRepository.findByUsername(username)
            .orElseThrow {
                UsernameNotFoundException(
                    "User NOT Found"
                )
            }

        return UserPrincipal(user.username, user.password, user.permissions, user.enabled)
    }
}
