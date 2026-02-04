package com.demo.streamflix.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "channels")
data class Channel(
    @PrimaryKey
    val id: Int,
    
    @SerializedName("number")
    val number: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("logo_url")
    val logoUrl: String,
    
    @SerializedName("stream_url")
    val streamUrl: String,
    
    @SerializedName("category_id")
    val categoryId: Int,
    
    @SerializedName("is_hd")
    val isHd: Boolean,
    
    @SerializedName("is_active")
    val isActive: Boolean,
    
    @SerializedName("created_at")
    val createdAt: String,
    
    @SerializedName("updated_at")
    val updatedAt: String
)