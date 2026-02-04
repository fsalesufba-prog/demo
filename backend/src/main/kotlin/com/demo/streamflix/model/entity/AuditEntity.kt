package com.demo.streamflix.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@MappedSuperclass
abstract class AuditEntity {
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }
}

@Entity
@Table(name = "access_logs")
data class AccessLogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    var channel: ChannelEntity? = null,

    @Column(nullable = false, length = 50)
    var action: String, // LOGIN, VIEW_CHANNEL, SEARCH, etc.

    @Column(name = "ip_address", length = 45)
    var ipAddress: String? = null,

    @Column(name = "user_agent", columnDefinition = "TEXT")
    var userAgent: String? = null,

    @Column(name = "details", columnDefinition = "TEXT")
    var details: String? = null
) : AuditEntity()

@Entity
@Table(name = "search_logs")
data class SearchLogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null,

    @Column(nullable = false)
    var query: String,

    @Column(name = "result_count", nullable = false)
    var resultCount: Long = 0,

    @Column(name = "execution_time_ms")
    var executionTimeMs: Long = 0
) : AuditEntity()