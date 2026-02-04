package com.demo.streamflix.data.local.repository

import com.demo.streamflix.data.local.dao.ChannelDao
import com.demo.streamflix.data.local.dao.FavoriteDao
import com.demo.streamflix.data.local.entity.ChannelEntity
import com.demo.streamflix.data.model.Channel
import com.demo.streamflix.util.SharedPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalChannelRepository @Inject constructor(
    private val channelDao: ChannelDao,
    private val favoriteDao: FavoriteDao,
    private val sharedPrefs: SharedPrefs
) {

    fun getAllChannels(): Flow<List<Channel>> {
        return channelDao.getAllChannels()
            .map { entities ->
                entities.map { it.toChannel() }
            }
    }

    fun getChannelsByCategory(categoryId: Int): Flow<List<Channel>> {
        return channelDao.getChannelsByCategory(categoryId)
            .map { entities ->
                entities.map { it.toChannel() }
            }
    }

    suspend fun getChannelById(channelId: Int): Channel? {
        return channelDao.getChannelById(channelId)?.toChannel()
    }

    suspend fun insertChannels(channels: List<Channel>) {
        val entities = channels.map { it.toEntity() }
        channelDao.insertChannels(entities)
    }

    suspend fun updatePlayStats(channelId: Int) {
        channelDao.updatePlayStats(channelId, Date())
    }

    fun getRecentlyPlayed(): Flow<List<Channel>> {
        return channelDao.getRecentlyPlayed()
            .map { entities ->
                entities.map { it.toChannel() }
            }
    }

    fun getMostPlayed(): Flow<List<Channel>> {
        return channelDao.getMostPlayed()
            .map { entities ->
                entities.map { it.toChannel() }
            }
    }

    fun getUserFavorites(): Flow<List<Channel>> {
        val userId = sharedPrefs.getString("user_id", "")
        return favoriteDao.getUserFavorites(userId)
            .map { entities ->
                entities.map { it.toChannel() }
            }
    }

    suspend fun toggleFavorite(channelId: Int): Boolean {
        val userId = sharedPrefs.getString("user_id", "")
        return favoriteDao.toggleFavorite(channelId, userId)
    }

    suspend fun isFavorite(channelId: Int): Boolean {
        val userId = sharedPrefs.getString("user_id", "")
        return favoriteDao.isFavorite(channelId, userId) > 0
    }

    suspend fun clearCache() {
        channelDao.clearAll()
    }
}

// Extensões para conversão
private fun ChannelEntity.toChannel(): Channel {
    return Channel(
        id = this.id,
        number = this.number,
        name = this.name,
        description = this.description,
        logoUrl = this.logoUrl,
        streamUrl = this.streamUrl,
        categoryId = this.categoryId,
        isHd = this.isHd,
        isActive = this.isActive,
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString()
    )
}

private fun Channel.toEntity(): ChannelEntity {
    return ChannelEntity(
        id = this.id,
        number = this.number,
        name = this.name,
        description = this.description,
        logoUrl = this.logoUrl,
        streamUrl = this.streamUrl,
        categoryId = this.categoryId,
        isHd = this.isHd,
        isActive = this.isActive,
        createdAt = Date(),
        updatedAt = Date()
    )
}