package com.demo.streamflix.model.dto

data class ChannelDto(
    val id: Long,
    val number: String,
    val name: String,
    val description: String? = null,
    val streamUrl: String,
    val logoUrl: String? = null,
    val categoryId: Long? = null,
    val isHd: Boolean,
    val isActive: Boolean,
    val viewsCount: Long
)