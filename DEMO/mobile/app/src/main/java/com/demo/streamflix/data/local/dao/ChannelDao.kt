package com.demo.streamflix.data.local.dao

import androidx.room.*
import com.demo.streamflix.data.local.entity.ChannelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelDao {

    @Query("SELECT * FROM channels WHERE is_active = 1 ORDER BY number ASC")
    fun getAllChannels(): Flow<List<ChannelEntity>>

    @Query("SELECT * FROM channels WHERE category_id = :categoryId AND is_active = 1 ORDER BY number ASC")
    fun getChannelsByCategory(categoryId: Int): Flow<List<ChannelEntity>>

    @Query("SELECT * FROM channels WHERE id = :channelId")
    suspend fun getChannelById(channelId: Int): ChannelEntity?

    @Query("SELECT * FROM channels WHERE number = :channelNumber")
    suspend fun getChannelByNumber(channelNumber: Int): ChannelEntity?

    @Query("SELECT * FROM channels WHERE is_active = 1 ORDER BY last_played DESC LIMIT 10")
    fun getRecentlyPlayed(): Flow<List<ChannelEntity>>

    @Query("SELECT * FROM channels WHERE is_active = 1 ORDER BY play_count DESC LIMIT 10")
    fun getMostPlayed(): Flow<List<ChannelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannels(channels: List<ChannelEntity>)

    @Update
    suspend fun updateChannel(channel: ChannelEntity)

    @Query("UPDATE channels SET last_played = :timestamp, play_count = play_count + 1 WHERE id = :channelId")
    suspend fun updatePlayStats(channelId: Int, timestamp: java.util.Date)

    @Query("DELETE FROM channels")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM channels WHERE is_active = 1")
    suspend fun getActiveChannelCount(): Int
}