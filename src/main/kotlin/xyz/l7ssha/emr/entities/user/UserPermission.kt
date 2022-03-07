package xyz.l7ssha.emr.entities.user

import xyz.l7ssha.emr.configuration.security.PermissionType
import javax.persistence.*

@Entity
@Table(name = "user_permissions")
open class UserPermission (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open val id: Long,

    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    open val name: PermissionType
)
