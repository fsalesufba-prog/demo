package com.demo.streamflix.admin.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
data class User(
    val id: Long? = null,
    val username: String,
    val email: String,
    val password: String? = null,
    val fullName: String,
    val isAdmin: Boolean = false,
    val isActive: Boolean = true,
    val subscriptionEnd: Instant? = null,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now()
)

@Serializable
data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val fullName: String,
    val isAdmin: Boolean,
    val isActive: Boolean,
    val subscriptionEnd: String?,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class UserRequest(
    val username: String,
    val email: String,
    val password: String? = null,
    val fullName: String,
    val isAdmin: Boolean = false,
    val isActive: Boolean = true,
    val subscriptionEnd: String? = null
)