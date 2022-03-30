package xyz.l7ssha.emr.entities.user

import javax.persistence.*

@Entity
@Table(name = "users")
open class User(
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_permissions", referencedColumnName = "id")
    open val permissions: Set<UserPermission> = mutableSetOf(),

    @Column(name = "password_expired", nullable = false)
    open var passwordExpired: Boolean = true
)
