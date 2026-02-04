package com.demo.streamflix.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "channels")
data class ChannelEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val number: Int,

    @Column(nullable = false)
    val name: String,

    @Column(columnDefinition = "TEXT")
    val description: String,

    @Column(name = "logo_url")
    val logoUrl: String,

    @Column(name = "stream_url", nullable = false)
    val streamUrl: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: CategoryEntity,

    @Column(name = "is_hd", nullable = false)
    val isHd: Boolean = false,

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean = true,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)