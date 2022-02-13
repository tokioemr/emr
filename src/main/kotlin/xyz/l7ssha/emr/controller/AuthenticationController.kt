package xyz.l7ssha.emr.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.bind.annotation.*
import xyz.l7ssha.emr.dto.security.*
import xyz.l7ssha.emr.events.commands.ConfirmForgotPasswordCommand
import xyz.l7ssha.emr.events.commands.SendForgotPasswordEmailCommand
import xyz.l7ssha.emr.service.AuthService
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    @Autowired val authService: AuthService,
    @Autowired val eventPublisher: ApplicationEventPublisher
) {

    @PostMapping("/login")
    fun authAction(@Valid @RequestBody jwtLoginInputDto: JwtLoginInputDto): JwtResultOutputDto {
        val (jwtToken, refreshToken) = authService.authWithPassword(jwtLoginInputDto.email, jwtLoginInputDto.password)

        return JwtResultOutputDto(jwtToken, refreshToken)
    }

    @PostMapping("/refresh")
    fun refreshAction(@Valid @RequestBody jwtRefreshInputDto: JwtRefreshInputDto): JwtResultOutputDto {
        val (jwtToken, refreshToken) = authService.authWithRefreshToken(jwtRefreshInputDto.refreshToken, jwtRefreshInputDto.email)

        return JwtResultOutputDto(jwtToken, refreshToken)
    }

    @PostMapping("/register")
    fun registerAction(@Valid @RequestBody registerInputDto: RegisterInputDto): JwtResultOutputDto {
        val (jwtToken, refreshToken) = authService.registerUser(registerInputDto.email, registerInputDto.password)

        return JwtResultOutputDto(jwtToken, refreshToken)
    }

    @PostMapping("/forgot-password")
    fun forgotPasswordAction(@Valid @RequestBody forgotPasswordInputDto: ForgotPasswordInputDto) {
        eventPublisher.publishEvent(SendForgotPasswordEmailCommand(forgotPasswordInputDto.email))
    }

    @GetMapping("/forgot-password-confirm")
    fun forgotPasswordConfirm(@Valid @RequestBody forgotPasswordConfirmInputDto: ForgotPasswordConfirmInputDto) {
        eventPublisher.publishEvent(
            ConfirmForgotPasswordCommand(
                forgotPasswordConfirmInputDto.email,
                forgotPasswordConfirmInputDto.password,
                forgotPasswordConfirmInputDto.token
            )
        )
    }
}
