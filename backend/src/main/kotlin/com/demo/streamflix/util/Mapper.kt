package com.demo.streamflix.util

import com.demo.streamflix.model.dto.UserDto
import com.demo.streamflix.model.entity.UserEntity
import com.demo.streamflix.model.response.CategoryResponse
import com.demo.streamflix.model.response.ChannelResponse
import com.demo.streamflix.model.entity.CategoryEntity
import com.demo.streamflix.model.entity.ChannelEntity

object Mapper {
    
    fun UserEntity.toDto(): UserDto {
        return UserDto(
            id = id!!,
            email = email,
            fullName = fullName,
            phone = phone,
            isActive = isActive,
            isAdmin = isAdmin,
            lastLogin = lastLogin,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
    
    fun ChannelEntity.toResponse(): ChannelResponse {
        return ChannelResponse(
            id = id!!,
            number = number,
            name = name,
            slug = slug,
            description = description,
            streamUrl = streamUrl,
            logoUrl = logoUrl,
            category = category?.toResponse(),
            isHd = isHd,
            isActive = isActive,
            viewsCount = viewsCount,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
    
    fun CategoryEntity.toResponse(): CategoryResponse {
        return CategoryResponse(
            id = id!!,
            name = name,
            slug = slug,
            description = description,
            iconUrl = iconUrl,
            sortOrder = sortOrder,
            isActive = isActive,
            createdAt = createdAt
        )
    }
}