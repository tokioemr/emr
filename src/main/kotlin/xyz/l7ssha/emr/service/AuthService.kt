package xyz.l7ssha.emr.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import xyz.l7ssha.emr.configuration.exception.JwtException
import xyz.l7ssha.emr.configuration.exception.ValidationException
import xyz.l7ssha.emr.configuration.exception.ValidationExceptionWithData
import xyz.l7ssha.emr.entities.RefreshToken
import xyz.l7ssha.emr.entities.User
import xyz.l7ssha.emr.repositories.RefreshTokenRepository
import xyz.l7ssha.emr.repositories.UserRepository
import java.time.Instant
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class AuthService(
    @Autowired val userRepository: UserRepository,
    @Autowired val userDetailsService: UserDetailsService,
    @Autowired val refreshTokenRepository: RefreshTokenRepository,
    @Autowired val resetPasswordTokenService: ResetPasswordTokenService,
    @Autowired val passwordEncoder: PasswordEncoder,
    @Value("\${jwt.secret}") val jwtSecret: String,
    @Value("\${jwt.expirationMs}") val jwtExpirationMs: Long = 60000L,
    @Value("\${jwt.refreshExpirationMs}") val jwtRefreshExpirationMs: Long = 60000L
) {
    fun registerUser(email: String, password: String) : Pair<String, String> {
        if (userRepository.findByEmail(email).isPresent) {
            throw ValidationException("User with given email already exists")
        }

        val user = User(0L, email, passwordEncoder.encode(password), true, emptyList(), false).apply {
            userRepository.save(this)
        }

        val refreshToken = updateOrCreateRefreshTokenEntity(user)
        return Pair(generateJwtToken(user), refreshToken.refreshToken)
    }

    fun authWithPassword(email: String, password: String) : Pair<String, String> {
        val user = userRepository.findByEmail(email)
        if (user.isEmpty) {
            throw JwtException("Missing user")
        }

        if (user.get().passwordExpired)  {
            throw ValidationExceptionWithData(
                "Password expired",
                mapOf("token" to resetPasswordTokenService.generateResetPasswordToken(user.get()).token)
            )
        }

        if (user.get().password != passwordEncoder.encode(password)) {
            throw JwtException("Invalid password")
        }

        val refreshToken = updateOrCreateRefreshTokenEntity(user.get())

        return Pair(generateJwtToken(user.get()), refreshToken.refreshToken)
    }

    fun authWithRefreshToken(refreshToken: String, email: String): Pair<String, String> {
        val refreshTokenEntity = refreshTokenRepository.getByRefreshToken(refreshToken).orElseThrow {
            throw JwtException("Invalid refresh token")
        }

        if (refreshTokenEntity.expirationDate < Instant.now()) {
            throw JwtException("Refresh token expired")
        }

        if (refreshTokenEntity.user.email != email) {
            throw JwtException("Given email is invalid for given token")
        }

        refreshTokenEntity.refreshToken = generateRefreshToken();
        refreshTokenEntity.expirationDate = generateRefreshTokenExpirationInstant()
        refreshTokenRepository.save(refreshTokenEntity)

        return Pair(
            generateJwtToken(refreshTokenEntity.user),
            refreshTokenEntity.refreshToken
        )
    }

    fun validateJwtForRequest(request: HttpServletRequest): Boolean {
        try {
            val jwt = parseJwtFromRequest(request)

            if (jwt != null && validateJwtToken(jwt)) {
                val email: String = getUserNameFromJwtToken(jwt)
                val userDetails = userDetailsService.loadUserByUsername(email)

                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication

                return true
            }
        } catch (_: Exception) {}

        return false
    }

    private fun parseJwtFromRequest(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        return if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7, headerAuth.length)
        } else null
    }

    private fun generateJwtToken(user: User): String {
        return Jwts.builder()
            .setSubject(user.email)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .addClaims(mapOf("permissions" to user.permissions.map { it.name }))
            .compact()
    }

    private fun getUserNameFromJwtToken(token: String?): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }

    private fun validateJwtToken(authToken: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun generateRefreshToken() = UUID.randomUUID().toString()
    private fun generateRefreshTokenExpirationInstant() = Instant.now()
        .plusMillis(jwtExpirationMs)
        .plusMillis(jwtRefreshExpirationMs)

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
