package com.demo.streamflix.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index
import java.util.Date

@Entity(
    tableName = "channels",
    indices = [
        Index(value = ["category_id"]),
        Index(value = ["number"], unique = true),
        Index(value = ["is_active"])
    ]
)
data class ChannelEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "number")
    val number: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "logo_url")
    val logoUrl: String,

    @ColumnInfo(name = "stream_url")
    val streamUrl: String,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    @ColumnInfo(name = "is_hd")
    val isHd: Boolean,

    @ColumnInfo(name = "is_active")
    val isActive: Boolean,

    @ColumnInfo(name = "created_at")
    val createdAt: Date,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Date,

    @ColumnInfo(name = "last_played")
    val lastPlayed: Date? = null,

    @ColumnInfo(name = "play_count")
    val playCount: Int = 0
)