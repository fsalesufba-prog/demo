package com.demo.streamflix.data.repository

import com.demo.streamflix.data.model.Channel
import com.demo.streamflix.data.network.ApiService
import com.demo.streamflix.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelRepository @Inject constructor(
    private val apiService: ApiService
) {

    private var cachedChannels: List<Channel>? = null

    fun getChannels(refresh: Boolean = false): Flow<NetworkResult<List<Channel>>> = flow {
        emit(NetworkResult.Loading())

        // Return cached data if available and not refreshing
        if (!refresh && cachedChannels != null) {
            emit(NetworkResult.Success(cachedChannels!!))
            return@flow
        }

        try {
            val response = apiService.getChannels()
            if (response.isSuccessful && response.body() != null) {
                val channels = response.body()!!.data
                cachedChannels = channels
                emit(NetworkResult.Success(channels))
            } else {
                emit(NetworkResult.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Network error"))
        }
    }

    fun getFeaturedChannels(refresh: Boolean = false): Flow<NetworkResult<List<Channel>>> = flow {
        emit(NetworkResult.Loading())

        try {
            val response = apiService.getFeaturedChannels()
            if (response.isSuccessful && response.body() != null) {
                val channels = response.body()!!.data
                emit(NetworkResult.Success(channels))
            } else {
                emit(NetworkResult.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Network error"))
        }
    }

    suspend fun searchChannels(query: String): NetworkResult<List<Channel>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.searchChannels(query)
                if (response.isSuccessful && response.body() != null) {
                    NetworkResult.Success(response.body()!!.data)
                } else {
                    NetworkResult.Error(response.message())
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Network error")
            }
        }
    }

    suspend fun getChannelById(id: Int): NetworkResult<Channel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getChannelById(id)
                if (response.isSuccessful && response.body() != null) {
                    NetworkResult.Success(response.body()!!.data)
                } else {
                    NetworkResult.Error("Channel not found")
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Network error")
            }
        }
    }

    fun getChannelsByCategory(categoryId: Int): Flow<NetworkResult<List<Channel>>> = flow {
        emit(NetworkResult.Loading())

        try {
            val response = apiService.getChannelsByCategory(categoryId)
            if (response.isSuccessful && response.body() != null) {
                val channels = response.body()!!.data
                emit(NetworkResult.Success(channels))
            } else {
                emit(NetworkResult.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Network error"))
        }
    }
}