package xyz.l7ssha.emr.configuration.security

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import java.util.logging.Logger


@Component
class JwtTokenUtil {
    companion object {
        private val logger: Logger = Logger.getLogger(JwtTokenUtil::class.java.canonicalName)
    }

    @Value("jwt.secret")
    private lateinit var jwtSecret: String

    private val jwtExpirationMs = 60000

    fun generateJwtToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUserNameFromJwtToken(token: String?): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (e: SignatureException) {
            logger.warning("Invalid JWT signature: ${e.message}")
        } catch (e: MalformedJwtException) {
            logger.warning("Invalid JWT token: ${e.message}")
        } catch (e: ExpiredJwtException) {
            logger.warning("JWT token is expired: ${e.message}")
        } catch (e: UnsupportedJwtException) {
            logger.warning("JWT token is unsupported: ${e.message}")
        } catch (e: IllegalArgumentException) {
            logger.warning("JWT claims string is empty: ${e.message}")
        }

        return false
    }
}
