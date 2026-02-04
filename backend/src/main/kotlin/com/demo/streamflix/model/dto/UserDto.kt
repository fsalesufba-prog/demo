package com.demo.streamflix.model.dto

import java.time.LocalDateTime

data class UserDto(
    val id: Long,
    val email: String,
    val fullName: String,
    val phone: String? = null,
    val isActive: Boolean,
    val isAdmin: Boolean,
    val lastLogin: LocalDateTime? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)