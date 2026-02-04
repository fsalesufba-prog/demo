package com.demo.streamflix.repository

import com.demo.streamflix.model.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<CategoryEntity, Long> {

    fun findBySlug(slug: String): CategoryEntity?

    fun existsBySlug(slug: String): Boolean

    fun findByIsActiveTrueOrderBySortOrderAsc(): List<CategoryEntity>

    fun countByIsActiveTrue(): Long

    @Query("SELECT c FROM CategoryEntity c WHERE c.isActive = true " +
           "AND LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.slug) LIKE LOWER(CONCAT('%', :query, '%'))")
    fun searchCategories(@Param("query") query: String): List<CategoryEntity>

    fun findByNameContainingIgnoreCase(name: String): List<CategoryEntity>

    @Query("SELECT c FROM CategoryEntity c WHERE c.isActive = true " +
           "AND c.id IN (SELECT DISTINCT ch.category.id FROM ChannelEntity ch WHERE ch.isActive = true)")
    fun findCategoriesWithActiveChannels(): List<CategoryEntity>

    @Query("SELECT c, COUNT(ch) as channelCount FROM CategoryEntity c " +
           "LEFT JOIN ChannelEntity ch ON ch.category = c AND ch.isActive = true " +
           "WHERE c.isActive = true " +
           "GROUP BY c.id " +
           "ORDER BY c.sortOrder")
    fun findAllWithChannelCount(): List<Array<Any>>

    @Query("SELECT MAX(c.sortOrder) FROM CategoryEntity c")
    fun findMaxSortOrder(): Int?
}