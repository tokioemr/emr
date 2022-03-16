package xyz.l7ssha.emr.configuration.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.entities.user.User
import xyz.l7ssha.emr.repositories.UserRepository

@Component
class AuthenticationFacade(@Autowired val userRepository: UserRepository) {
    val authentication: Authentication
        get() = SecurityContextHolder.getContext().authentication

    val loggedInUser: User
        get() = (authentication.principal as UserPrincipal)
            .let { userRepository.getById(it.getId()) }
}
