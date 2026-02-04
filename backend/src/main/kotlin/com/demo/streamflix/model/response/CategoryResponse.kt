package com.demo.streamflix.model.response

import java.time.LocalDateTime

data class CategoryResponse(
    val id: Long,
    val name: String,
    val slug: String,
    val description: String? = null,
    val iconUrl: String? = null,
    val sortOrder: Int,
    val isActive: Boolean,
    val channels: List<ChannelResponse>? = null,
    val createdAt: LocalDateTime
)