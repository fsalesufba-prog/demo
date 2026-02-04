package com.demo.streamflix.model.dto

import com.demo.streamflix.model.entity.SubscriptionEntity
import java.time.LocalDateTime

data class SubscriptionDto(
    val id: Long,
    val userId: Long,
    val userEmail: String,
    val userName: String,
    val startDate: java.time.LocalDate,
    val endDate: java.time.LocalDate,
    val status: SubscriptionEntity.SubscriptionStatus,
    val daysRemaining: Int,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)