package com.demo.streamflix.model.entity

import com.demo.streamflix.model.dto.SubscriptionDto
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "subscriptions")
data class SubscriptionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @Column(name = "start_date", nullable = false)
    var startDate: LocalDate,

    @Column(name = "end_date", nullable = false)
    var endDate: LocalDate,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: SubscriptionStatus = SubscriptionStatus.ACTIVE,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    enum class SubscriptionStatus {
        ACTIVE, EXPIRED, CANCELLED
    }

    fun toDto(): SubscriptionDto {
        return SubscriptionDto(
            id = id!!,
            userId = user.id!!,
            userEmail = user.email,
            userName = user.fullName,
            startDate = startDate,
            endDate = endDate,
            status = status,
            daysRemaining = LocalDate.now().until(endDate).days,
            isActive = status == SubscriptionStatus.ACTIVE && endDate.isAfter(LocalDate.now()),
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
        
        // Atualizar status automaticamente se expirado
        if (endDate.isBefore(LocalDate.now()) && status == SubscriptionStatus.ACTIVE) {
            status = SubscriptionStatus.EXPIRED
        }
    }

    fun isValid(): Boolean {
        return status == SubscriptionStatus.ACTIVE && endDate.isAfter(LocalDate.now())
    }
}