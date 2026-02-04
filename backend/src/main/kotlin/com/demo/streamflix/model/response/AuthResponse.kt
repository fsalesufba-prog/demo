package com.demo.streamflix.model.response

import com.demo.streamflix.model.dto.UserDto

data class AuthResponse(
    val token: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long,
    val user: UserDto
)