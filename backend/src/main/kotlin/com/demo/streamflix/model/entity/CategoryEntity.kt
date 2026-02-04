package com.demo.streamflix.model.entity

import com.demo.streamflix.model.dto.CategoryDto
import com.demo.streamflix.model.response.CategoryResponse
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "categories")
data class CategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var slug: String,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "icon_url")
    var iconUrl: String? = null,

    @Column(name = "sort_order", nullable = false)
    var sortOrder: Int = 0,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL])
    var channels: List<ChannelEntity> = mutableListOf()
) {
    fun toResponse(): CategoryResponse {
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

    fun toDto(): CategoryDto {
        return CategoryDto(
            id = id!!,
            name = name,
            slug = slug,
            description = description,
            iconUrl = iconUrl,
            sortOrder = sortOrder,
            isActive = isActive
        )
    }
}