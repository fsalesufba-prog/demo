package com.demo.streamflix.admin.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import com.demo.streamflix.admin.models.*
import com.demo.streamflix.admin.utils.Constants

class ApiService {
    private val scope = MainScope()
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
        defaultRequest {
            url("${Constants.API_BASE_URL}/api/admin/")
            contentType(ContentType.Application.Json)
            bearerAuth(AuthService.getToken() ?: "")
        }
    }
    
    // Users
    suspend fun getUsers(): List<UserResponse> = client.get("users").body()
    suspend fun getUser(id: Long): UserResponse = client.get("users/$id").body()
    suspend fun createUser(user: UserRequest): UserResponse = client.post("users") { 
        setBody(user) 
    }.body()
    suspend fun updateUser(id: Long, user: UserRequest): UserResponse = client.put("users/$id") { 
        setBody(user) 
    }.body()
    suspend fun deleteUser(id: Long): Boolean = client.delete("users/$id").body()
    
    // Channels
    suspend fun getChannels(): List<ChannelResponse> = client.get("channels").body()
    suspend fun getChannel(id: Long): ChannelResponse = client.get("channels/$id").body()
    suspend fun createChannel(channel: ChannelRequest): ChannelResponse = client.post("channels") { 
        setBody(channel) 
    }.body()
    suspend fun updateChannel(id: Long, channel: ChannelRequest): ChannelResponse = client.put("channels/$id") { 
        setBody(channel) 
    }.body()
    suspend fun deleteChannel(id: Long): Boolean = client.delete("channels/$id").body()
    
    // Categories
    suspend fun getCategories(): List<Category> = client.get("categories").body()
    suspend fun getCategoryWithChannels(id: Long): CategoryWithChannels = client.get("categories/$id").body()
    suspend fun createCategory(category: Category): Category = client.post("categories") { 
        setBody(category) 
    }.body()
    suspend fun updateCategory(id: Long, category: Category): Category = client.put("categories/$id") { 
        setBody(category) 
    }.body()
    suspend fun deleteCategory(id: Long): Boolean = client.delete("categories/$id").body()
    
    // Dashboard Stats
    suspend fun getDashboardStats(): DashboardStats = client.get("dashboard/stats").body()
    
    fun close() {
        client.close()
    }
}

@kotlinx.serialization.Serializable
data class DashboardStats(
    val totalUsers: Int,
    val activeUsers: Int,
    val totalChannels: Int,
    val premiumChannels: Int,
    val totalCategories: Int,
    val recentUsers: List<UserResponse>,
    val popularChannels: List<ChannelResponse>
)