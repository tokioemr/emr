package xyz.l7ssha.emr.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.l7ssha.emr.dto.security.JwtLoginInputDto
import xyz.l7ssha.emr.dto.security.JwtRefreshInputDto
import xyz.l7ssha.emr.dto.security.JwtResultOutputDto
import xyz.l7ssha.emr.dto.security.RegisterInputDto
import xyz.l7ssha.emr.service.AuthService
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(@Autowired val authService: AuthService) {
    @PostMapping("/login")
    fun authAction(@Valid @RequestBody jwtLoginInputDto: JwtLoginInputDto): JwtResultOutputDto {
        val (jwtToken, refreshToken) = authService.authWithPassword(jwtLoginInputDto.email, jwtLoginInputDto.password)

        return JwtResultOutputDto(jwtToken, refreshToken)
    }

    @PostMapping("/refresh")
    fun refreshAction(@Valid @RequestBody jwtRefreshInputDto: JwtRefreshInputDto): JwtResultOutputDto {
        val (jwtToken, refreshToken) = authService.authWithRefreshToken(jwtRefreshInputDto.refreshToken)

        return JwtResultOutputDto(jwtToken, refreshToken)
    }

    @PostMapping("/register")
    fun registerAction(@Valid @RequestBody registerInputDto: RegisterInputDto): JwtResultOutputDto {
        val (jwtToken, refreshToken) = authService.registerUser(registerInputDto.email, registerInputDto.password)

        return JwtResultOutputDto(jwtToken, refreshToken)
    }
}
