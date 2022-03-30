package xyz.l7ssha.emr.repositories

import xyz.l7ssha.emr.configuration.security.PermissionType
import xyz.l7ssha.emr.entities.user.UserPermission

interface UserPermissionRepository : EmrRepository<UserPermission> {
    fun getByName(name: PermissionType): UserPermission
}
