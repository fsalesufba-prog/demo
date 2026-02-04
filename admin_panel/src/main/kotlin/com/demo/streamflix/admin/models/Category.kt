package com.demo.streamflix.admin.models

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    val icon: String? = null,
    val color: String? = null,
    val orderIndex: Int = 0
)

@Serializable
data class CategoryWithChannels(
    val id: Long,
    val name: String,
    val description: String?,
    val icon: String?,
    val color: String?,
    val orderIndex: Int,
    val channels: List<ChannelResponse>
)