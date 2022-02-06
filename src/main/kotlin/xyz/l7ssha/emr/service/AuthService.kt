package xyz.l7ssha.emr.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import xyz.l7ssha.emr.configuration.exception.JwtException
import xyz.l7ssha.emr.configuration.security.JwtTokenUtil
import xyz.l7ssha.emr.entities.RefreshToken
import xyz.l7ssha.emr.entities.User
import xyz.l7ssha.emr.repositories.RefreshTokenRepository
import xyz.l7ssha.emr.repositories.UserRepository
import java.time.Instant
import java.util.*

@Service
class AuthService(
    @Autowired val authenticationManager: AuthenticationManager,
    @Autowired val jwtTokenUtil: JwtTokenUtil,
    @Autowired val userRepository: UserRepository,
    @Autowired val refreshTokenRepository: RefreshTokenRepository
) {
    fun authWithPassword(username: String, password: String) : Pair<String, String> {
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        SecurityContextHolder.getContext().authentication = auth

        val user = userRepository.findByUsername(username)
        if (user.isEmpty) {
            throw JwtException("Missing user")
        }

        val refreshToken = updateOrCreateRefreshTokenEntity(user.get())

        return Pair(jwtTokenUtil.generateJwtToken(user.get().username), refreshToken.refreshToken)
    }

    fun authWithRefreshToken(refreshToken: String): Pair<String, String> {
        val refreshTokenEntity = refreshTokenRepository.getByRefreshToken(refreshToken).orElseThrow {
            throw JwtException("Invalid refresh token")
        }

        if (refreshTokenEntity.expirationDate < Instant.now()) {
            throw JwtException("Refresh token expired")
        }

        refreshTokenEntity.refreshToken = generateRefreshToken();
        refreshTokenEntity.expirationDate = generateRefreshTokenExpirationInstant()
        refreshTokenRepository.save(refreshTokenEntity)

        return Pair(
            jwtTokenUtil.generateJwtToken(refreshTokenEntity.user.username),
            refreshTokenEntity.refreshToken
        )
    }

    private fun generateRefreshToken() = UUID.randomUUID().toString()
    private fun generateRefreshTokenExpirationInstant() = Instant.now()
        .plusMillis(JwtTokenUtil.jwtExpirationMs)
        .plusMillis(JwtTokenUtil.jwtRefreshExpirationMs)

    private fun updateOrCreateRefreshTokenEntity(user: User): RefreshToken {
        val refreshToken = generateRefreshToken();
        val expirationInstant = generateRefreshTokenExpirationInstant()

        val refreshTokenEntity = refreshTokenRepository.getByUser(user)

        if (refreshTokenEntity.isPresent) {
            return refreshTokenEntity.get().apply {
                this.refreshToken = refreshToken
                this.expirationDate = expirationInstant

                refreshTokenRepository.save(this)
            }
        }

        return RefreshToken(0L, user, generateRefreshToken(), generateRefreshTokenExpirationInstant()).apply {
            refreshTokenRepository.save(this)
        }
    }
}
