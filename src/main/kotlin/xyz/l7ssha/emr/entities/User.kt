package xyz.l7ssha.emr.entities

import javax.persistence.*

@Entity
@Table(name = "users")
open class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "username", nullable = false, unique = true)
    open var username: String? = null

    @Column(name = "password", nullable = false)
    open var password: String? = null

    @Column(name = "enabled", nullable = false)
    open var enabled: Boolean = false

    @ManyToMany(targetEntity = UserPermission::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_permissions", referencedColumnName = "id")
    open var permissions: List<UserPermission> = listOf()
}
