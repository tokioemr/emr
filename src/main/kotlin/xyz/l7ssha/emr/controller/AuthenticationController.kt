package xyz.l7ssha.emr.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.l7ssha.emr.configuration.security.JwtTokenUtil
import xyz.l7ssha.emr.dto.security.JwtRequestInputDto
import xyz.l7ssha.emr.dto.security.JwtResponseOutputDto
import xyz.l7ssha.emr.service.UserDetailsService
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthenticationController {
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var jwtInMemoryUserDetailsService: UserDetailsService

    @PostMapping("/login")
    fun authAction(@Valid @RequestBody jwtRequestInputDto: JwtRequestInputDto): JwtResponseOutputDto {
        authenticate(jwtRequestInputDto.username, jwtRequestInputDto.password)

        val userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(jwtRequestInputDto.username)
        return JwtResponseOutputDto(jwtTokenUtil.generateJwtToken(userDetails.username))
    }

    private fun authenticate(username: String, password: String) {
        try {
            val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
            SecurityContextHolder.getContext().authentication = auth
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }
}
