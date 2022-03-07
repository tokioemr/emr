package xyz.l7ssha.emr.entities.user

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "refresh_tokens")
open class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open val id: Long,

    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @OneToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    open val user: User,

    @Column(name = "refresh_token", nullable = false, unique = true)
    open var refreshToken: String,

    @Column(name = "expiration_date", nullable = false)
    open var expirationDate: Instant
)
