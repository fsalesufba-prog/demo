package com.demo.streamflix.model.entity

import com.demo.streamflix.model.dto.ChannelDto
import com.demo.streamflix.model.response.ChannelResponse
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "channels")
data class ChannelEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true, length = 3)
    var number: String, // Formato: 001, 002, etc.

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var slug: String,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "stream_url", nullable = false, columnDefinition = "TEXT")
    var streamUrl: String,

    @Column(name = "logo_url")
    var logoUrl: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: CategoryEntity? = null,

    @Column(name = "is_hd", nullable = false)
    var isHd: Boolean = false,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @Column(name = "views_count", nullable = false)
    var viewsCount: Long = 0,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun toResponse(): ChannelResponse {
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

    fun toDto(): ChannelDto {
        return ChannelDto(
            id = id!!,
            number = number,
            name = name,
            description = description,
            streamUrl = streamUrl,
            logoUrl = logoUrl,
            categoryId = category?.id,
            isHd = isHd,
            isActive = isActive,
            viewsCount = viewsCount
        )
    }

    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }

    @PrePersist
    fun onCreate() {
        // Garantir que slug seja gerado se não fornecido
        if (slug.isBlank()) {
            slug = name.lowercase()
                .replace(" ", "-")
                .replace("[^a-z0-9-]".toRegex(), "")
        }
        
        // Garantir que número tenha 3 dígitos
        number = number.padStart(3, '0')
    }
}