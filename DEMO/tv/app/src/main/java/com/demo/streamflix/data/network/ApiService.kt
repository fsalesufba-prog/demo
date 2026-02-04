package com.demo.streamflix.data.network

import com.demo.streamflix.data.model.Channel
import com.demo.streamflix.data.model.Category
import com.demo.streamflix.data.model.response.ApiResponse
import com.demo.streamflix.data.model.response.AuthResponse
import com.demo.streamflix.data.model.request.LoginRequest
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Authentication
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/logout")
    suspend fun logout(): Response<ApiResponse<Unit>>

    @GET("api/auth/validate")
    suspend fun validateToken(@Header("Authorization") token: String): Response<ApiResponse<Boolean>>

    // Categories
    @GET("api/categories")
    suspend fun getCategories(): Response<ApiResponse<List<Category>>>

    @GET("api/categories/{id}")
    suspend fun getCategoryById(@Path("id") id: Int): Response<ApiResponse<Category>>

    // Channels
    @GET("api/channels")
    suspend fun getChannels(): Response<ApiResponse<List<Channel>>>

    @GET("api/channels/featured")
    suspend fun getFeaturedChannels(): Response<ApiResponse<List<Channel>>>

    @GET("api/channels/search")
    suspend fun searchChannels(@Query("query") query: String): Response<ApiResponse<List<Channel>>>

    @GET("api/channels/category/{categoryId}")
    suspend fun getChannelsByCategory(@Path("categoryId") categoryId: Int): Response<ApiResponse<List<Channel>>>

    @GET("api/channels/{id}")
    suspend fun getChannelById(@Path("id") id: Int): Response<ApiResponse<Channel>>

    // User Profile
    @GET("api/users/profile")
    suspend fun getUserProfile(): Response<ApiResponse<com.demo.streamflix.data.model.User>>

    @PUT("api/users/profile")
    suspend fun updateProfile(@Body user: com.demo.streamflix.data.model.User): Response<ApiResponse<com.demo.streamflix.data.model.User>>

    // Membership
    @GET("api/subscription/validate")
    suspend fun validateMembership(): Response<ApiResponse<Boolean>>
}