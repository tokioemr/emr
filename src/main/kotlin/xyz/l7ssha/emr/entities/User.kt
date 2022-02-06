package xyz.l7ssha.emr.entities

import javax.persistence.*
import javax.transaction.Transactional

@Entity
@Table(name = "users")
open class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open val id: Long,

    @Column(name = "username", nullable = false, unique = true)
    open var username: String,

    @Column(name = "password", nullable = false)
    open var password: String,

    @Column(name = "enabled", nullable = false)
    open var enabled: Boolean = false,

    @ManyToMany(targetEntity = UserPermission::class, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "user_permissions", referencedColumnName = "id")
    open val permissions: List<UserPermission> = listOf()
)
