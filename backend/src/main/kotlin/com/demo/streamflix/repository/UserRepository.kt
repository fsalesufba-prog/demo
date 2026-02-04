package com.demo.streamflix.repository

import com.demo.streamflix.model.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): UserEntity?

    fun existsByEmail(email: String): Boolean

    fun findByIsActiveTrue(): List<UserEntity>

    fun countByIsActive(isActive: Boolean): Long

    fun countByIsAdmin(isAdmin: Boolean): Long

    @Query("SELECT u FROM UserEntity u WHERE " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.phone) LIKE LOWER(CONCAT('%', :query, '%'))")
    fun searchUsers(@Param("query") query: String, pageable: Pageable): Page<UserEntity>

    @Query("SELECT u FROM UserEntity u WHERE u.isActive = true " +
           "AND (LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "ORDER BY u.fullName")
    fun searchActiveUsers(@Param("query") query: String): List<UserEntity>

    fun findByIsAdminTrue(): List<UserEntity>

    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.createdAt >= CURRENT_DATE - 30")
    fun countNewUsersLast30Days(): Long

    @Query("SELECT u FROM UserEntity u WHERE u.lastLogin IS NOT NULL ORDER BY u.lastLogin DESC")
    fun findRecentlyActiveUsers(pageable: Pageable): Page<UserEntity>
}