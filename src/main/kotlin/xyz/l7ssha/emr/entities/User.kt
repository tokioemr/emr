package xyz.l7ssha.emr.entities

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
@Table(name = "users")
open class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open val id: Long,

    @Column(name = "email", nullable = false, unique = true)
    open var email: String,

    @Column(name = "password", nullable = false)
    open var password: String,

    @Column(name = "enabled", nullable = false)
    open var enabled: Boolean = false,

    @ManyToMany(targetEntity = UserPermission::class, cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "user_permissions", referencedColumnName = "id")
    @Fetch(FetchMode.JOIN)
    open val permissions: List<UserPermission> = listOf(),

    @Column(name = "password_expired", nullable = false)
    open val passwordExpired: Boolean = true
)
