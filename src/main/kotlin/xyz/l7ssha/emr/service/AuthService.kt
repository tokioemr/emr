package xyz.l7ssha.emr.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import xyz.l7ssha.emr.configuration.exception.JwtException
import xyz.l7ssha.emr.entities.RefreshToken
import xyz.l7ssha.emr.entities.User
import xyz.l7ssha.emr.repositories.RefreshTokenRepository
import xyz.l7ssha.emr.repositories.UserRepository
import java.time.Instant
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class AuthService(
    @Autowired val authenticationManager: AuthenticationManager,
    @Autowired val userRepository: UserRepository,
    @Autowired val userDetailsService: UserDetailsService,
    @Autowired val refreshTokenRepository: RefreshTokenRepository,
    @Value("jwt.secret") val jwtSecret: String,
    @Value("\${jwt.expirationMs}") val jwtExpirationMs: Long = 60000L,
    @Value("\${jwt.refreshExpirationMs}") val jwtRefreshExpirationMs: Long = 60000L
) {
    fun authWithPassword(username: String, password: String) : Pair<String, String> {
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        SecurityContextHolder.getContext().authentication = auth

        val user = userRepository.findByUsername(username)
        if (user.isEmpty) {
            throw JwtException("Missing user")
        }

        val refreshToken = updateOrCreateRefreshTokenEntity(user.get())

        return Pair(generateJwtToken(user.get().username), refreshToken.refreshToken)
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
            generateJwtToken(refreshTokenEntity.user.username),
            refreshTokenEntity.refreshToken
        )
    }

    fun validateJwtForRequest(request: HttpServletRequest): Boolean {
        try {
            val jwt = parseJwtFromRequest(request)

            if (jwt != null && validateJwtToken(jwt)) {
                val username: String = getUserNameFromJwtToken(jwt)
                val userDetails = userDetailsService.loadUserByUsername(username)

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

    private fun generateJwtToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
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
