package xyz.l7ssha.emr.configuration.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import xyz.l7ssha.emr.entities.user.User
import xyz.l7ssha.emr.service.entity.UserEntityService

@Component
class AuthenticationFacade(@Autowired val userService: UserEntityService) {
    val authentication: Authentication
        get() = SecurityContextHolder.getContext().authentication

    val loggedInUser: User
        get() = (authentication.principal as UserDetails)
            .let { userService.findByEmail(it.username).orElseThrow() }
}
