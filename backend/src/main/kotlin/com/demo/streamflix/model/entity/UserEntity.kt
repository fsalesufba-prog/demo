package com.demo.streamflix.model.entity

import com.demo.streamflix.model.dto.UserDto
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Column(name = "full_name", nullable = false)
    var fullName: String,

    @Column
    var phone: String? = null,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @Column(name = "is_admin", nullable = false)
    var isAdmin: Boolean = false,

    @Column(name = "last_login")
    var lastLogin: LocalDateTime? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var subscriptions: List<SubscriptionEntity> = mutableListOf()
) {
    fun toDto(): UserDto {
        return UserDto(
            id = id!!,
            email = email,
            fullName = fullName,
            phone = phone,
            isActive = isActive,
            isAdmin = isAdmin,
            lastLogin = lastLogin,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }
}