package com.demo.streamflix.security

import com.demo.streamflix.model.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    private val id: Long,
    private val email: String,
    private val password: String,
    private val fullName: String,
    private val isActive: Boolean,
    private val isAdmin: Boolean,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    constructor(user: UserEntity) : this(
        id = user.id!!,
        email = user.email,
        password = user.password,
        fullName = user.fullName,
        isActive = user.isActive,
        isAdmin = user.isAdmin,
        authorities = getAuthorities(user)
    )

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getPassword(): String = password

    override fun getUsername(): String = email

    fun getId(): Long = id

    fun getEmail(): String = email

    fun getFullName(): String = fullName

    fun isAdmin(): Boolean = isAdmin

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = isActive

    companion object {
        fun build(user: UserEntity): UserDetailsImpl {
            val authorities = getAuthorities(user)
            return UserDetailsImpl(
                user.id!!,
                user.email,
                user.password,
                user.fullName,
                user.isActive,
                user.isAdmin,
                authorities
            )
        }

        private fun getAuthorities(user: UserEntity): List<GrantedAuthority> {
            val authorities = mutableListOf<GrantedAuthority>()
            authorities.add(SimpleGrantedAuthority("ROLE_USER"))
            
            if (user.isAdmin) {
                authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
            }
            
            return authorities
        }
    }
}