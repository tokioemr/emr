package xyz.l7ssha.emr.service

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationException
import xyz.l7ssha.emr.configuration.exception.CatchableApplicationWithDataException
import xyz.l7ssha.emr.configuration.exception.JwtApplicationException
import xyz.l7ssha.emr.entities.user.RefreshToken
import xyz.l7ssha.emr.entities.user.User
import xyz.l7ssha.emr.repositories.auth.RefreshTokenRepository
import xyz.l7ssha.emr.service.entity.UserEntityService
import java.security.SignatureException
import java.time.Instant
import java.util.*
import javax.servlet.http.HttpServletRequest

private const val BEARER_TOKEN_OFFSET = 7

@Service
class AuthService(
    @Autowired val userService: UserEntityService,
    @Autowired val userDetailsService: UserDetailsService,
    @Autowired val refreshTokenRepository: RefreshTokenRepository,
    @Autowired val resetPasswordTokenService: ResetPasswordTokenService,
    @Autowired val passwordEncoder: PasswordEncoder,
    @Value("\${jwt.secret}") val jwtSecret: String,
    @Value("\${jwt.expirationMs}") val jwtExpirationMs: Long = 60000L,
    @Value("\${jwt.refreshExpirationMs}") val jwtRefreshExpirationMs: Long = 60000L
) {
    fun registerUser(email: String, password: String): Pair<String, String> {
        if (userService.findByEmail(email).isPresent) {
            throw CatchableApplicationException("User with given email already exists")
        }

        val user = User(0L, email, passwordEncoder.encode(password), true, emptySet(), false).apply {
            userService.save(this)
        }

        val refreshToken = updateOrCreateRefreshTokenEntity(user)
        return Pair(generateJwtToken(user), refreshToken.refreshToken)
    }

    fun authWithPassword(email: String, password: String): Pair<String, String> {
        val user = userService.findByEmail(email)
        if (user.isEmpty) {
            throw JwtApplicationException("Missing user")
        }

        if (user.get().passwordExpired) {
            throw CatchableApplicationWithDataException(
                "Password expired",
                HttpStatus.UNAUTHORIZED,
                mapOf("token" to resetPasswordTokenService.generateResetPasswordToken(user.get()).token)
            )
        }

        if (!passwordEncoder.matches(password, user.get().password)) {
            throw JwtApplicationException("Invalid password")
        }

        val refreshToken = updateOrCreateRefreshTokenEntity(user.get())

        return Pair(generateJwtToken(user.get()), refreshToken.refreshToken)
    }

    fun authWithRefreshToken(refreshToken: String, email: String): Pair<String, String> {
        val refreshTokenEntity = refreshTokenRepository.getByRefreshToken(refreshToken).orElseThrow {
            throw JwtApplicationException("Invalid refresh token")
        }

        if (refreshTokenEntity.expirationDate < Instant.now()) {
            throw JwtApplicationException("Refresh token expired")
        }

        if (refreshTokenEntity.user.email != email) {
            throw JwtApplicationException("Given email is invalid for given token")
        }

        refreshTokenEntity.refreshToken = generateRefreshToken()
        refreshTokenEntity.expirationDate = generateRefreshTokenExpirationInstant()
        refreshTokenRepository.save(refreshTokenEntity)

        return Pair(
            generateJwtToken(refreshTokenEntity.user),
            refreshTokenEntity.refreshToken
        )
    }

    fun validateJwtForRequest(request: HttpServletRequest) {
        try {
            val jwt = parseJwtFromRequest(request)

            if (jwt != null) {
                validateJwtToken(jwt)

                val email: String = getUserNameFromJwtToken(jwt)
                val userDetails = userDetailsService.loadUserByUsername(email)

                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )

                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: UnsupportedJwtException) {
            throw JwtApplicationException(e.message ?: "")
        } catch (e: MalformedJwtException) {
            throw JwtApplicationException(e.message ?: "")
        } catch (e: SignatureException) {
            throw JwtApplicationException(e.message ?: "")
        } catch (e: ExpiredJwtException) {
            throw JwtApplicationException(e.message ?: "")
        } catch (e: IllegalArgumentException) {
            throw JwtApplicationException(e.message ?: "")
        }
    }

    private fun parseJwtFromRequest(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")

        return if (headerAuth != null && headerAuth.isNotBlank() && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(BEARER_TOKEN_OFFSET, headerAuth.length)
        } else null
    }

    private fun generateJwtToken(user: User): String {
        val now = Instant.now()

        return Jwts.builder()
            .setSubject(user.email)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plusMillis(jwtExpirationMs)))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .addClaims(mapOf("permissions" to user.permissions.map { it.name }))
            .compact()
    }

    private fun getUserNameFromJwtToken(token: String?): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }

    private fun validateJwtToken(authToken: String?) {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
    }

    private fun generateRefreshToken() = UUID.randomUUID().toString()
    private fun generateRefreshTokenExpirationInstant() = Instant.now()
        .plusMillis(jwtExpirationMs)
        .plusMillis(jwtRefreshExpirationMs)

    private fun updateOrCreateRefreshTokenEntity(user: User): RefreshToken {
        val refreshToken = generateRefreshToken()
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
