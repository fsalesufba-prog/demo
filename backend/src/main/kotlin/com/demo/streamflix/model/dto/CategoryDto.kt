package com.demo.streamflix.model.dto

data class CategoryDto(
    val id: Long,
    val name: String,
    val slug: String,
    val description: String? = null,
    val iconUrl: String? = null,
    val sortOrder: Int,
    val isActive: Boolean
)