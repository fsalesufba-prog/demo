package com.demo.streamflix.data.local.dao

import androidx.room.*
import com.demo.streamflix.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT c.* FROM channels c INNER JOIN favorites f ON c.id = f.channel_id WHERE f.user_id = :userId ORDER BY f.added_at DESC")
    fun getUserFavorites(userId: String): Flow<List<com.demo.streamflix.data.local.entity.ChannelEntity>>

    @Query("SELECT COUNT(*) FROM favorites WHERE channel_id = :channelId AND user_id = :userId")
    suspend fun isFavorite(channelId: Int, userId: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: FavoriteEntity): Long

    @Query("DELETE FROM favorites WHERE channel_id = :channelId AND user_id = :userId")
    suspend fun removeFavorite(channelId: Int, userId: String): Int

    @Query("DELETE FROM favorites WHERE user_id = :userId")
    suspend fun clearUserFavorites(userId: String)

    @Transaction
    suspend fun toggleFavorite(channelId: Int, userId: String): Boolean {
        val isCurrentlyFavorite = isFavorite(channelId, userId) > 0
        return if (isCurrentlyFavorite) {
            removeFavorite(channelId, userId) > 0
            false
        } else {
            addFavorite(FavoriteEntity(channelId = channelId, userId = userId)) > 0
            true
        }
    }
}