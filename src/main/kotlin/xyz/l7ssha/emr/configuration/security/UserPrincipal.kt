package xyz.l7ssha.emr.configuration.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

import org.springframework.security.core.userdetails.UserDetails
import xyz.l7ssha.emr.entities.UserPermission


class UserPrincipal(private val innerUsername: String, private val innerPassword: String, private val innerPermissions: List<UserPermission>, private val innerEnabled: Boolean) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return innerPermissions.map { SimpleGrantedAuthority("ROLE_" + it.name) }.toMutableList()
    }
    override fun getPassword() = innerPassword
    override fun getUsername() = innerUsername
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = innerEnabled
}
