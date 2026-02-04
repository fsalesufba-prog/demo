package com.demo.streamflix.admin.models

import kotlinx.serialization.Serializable

@Serializable
data class Channel(
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    val url: String,
    val logoUrl: String? = null,
    val categoryId: Long,
    val isActive: Boolean = true,
    val isPremium: Boolean = false,
    val country: String = "PE",
    val language: String = "es",
    val orderIndex: Int = 0
)

@Serializable
data class ChannelResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val url: String,
    val logoUrl: String?,
    val category: Category,
    val isActive: Boolean,
    val isPremium: Boolean,
    val country: String,
    val language: String,
    val orderIndex: Int
)

@Serializable
data class ChannelRequest(
    val name: String,
    val description: String? = null,
    val url: String,
    val logoUrl: String? = null,
    val categoryId: Long,
    val isActive: Boolean = true,
    val isPremium: Boolean = false,
    val country: String = "PE",
    val language: String = "es",
    val orderIndex: Int = 0
)