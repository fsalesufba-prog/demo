package com.demo.streamflix.model.response

import java.time.LocalDateTime

data class ChannelResponse(
    val id: Long,
    val number: String,
    val name: String,
    val slug: String,
    val description: String? = null,
    val streamUrl: String,
    val logoUrl: String? = null,
    val category: CategoryResponse? = null,
    val isHd: Boolean,
    val isActive: Boolean,
    val viewsCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)