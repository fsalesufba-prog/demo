package com.demo.streamflix.admin.services

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.browser.localStorage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import com.demo.streamflix.admin.utils.Constants

class AuthService {
    companion object {
        private const val TOKEN_KEY = "admin_token"
        private const val USER_KEY = "admin_user"
        
        fun getToken(): String? = localStorage.getItem(TOKEN_KEY)
        
        fun saveToken(token: String) = localStorage.setItem(TOKEN_KEY, token)
        
        fun clearToken() = localStorage.removeItem(TOKEN_KEY)
        
        fun saveUser(user: String) = localStorage.setItem(USER_KEY, user)
        
        fun getUser(): String? = localStorage.getItem(USER_KEY)
        
        fun clearUser() = localStorage.removeItem(USER_KEY)
        
        fun isAuthenticated(): Boolean = getToken() != null
        
        fun logout() {
            clearToken()
            clearUser()
        }
    }
}

@kotlinx.serialization.Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@kotlinx.serialization.Serializable
data class LoginResponse(
    val token: String,
    val user: UserResponse
)

class AuthApiService {
    private val scope = MainScope()
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    
    suspend fun login(username: String, password: String): LoginResponse? {
        return try {
            val response: LoginResponse = client.post("${Constants.API_BASE_URL}/api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(username, password))
            }.body()
            
            AuthService.saveToken(response.token)
            AuthService.saveUser(Json.encodeToString(UserResponse.serializer(), response.user))
            
            response
        } catch (e: Exception) {
            console.error("Login error:", e)
            null
        }
    }
    
    suspend fun validateToken(): Boolean {
        return try {
            val token = AuthService.getToken()
            if (token.isNullOrEmpty()) return false
            
            val response: HttpResponse = client.get("${Constants.API_BASE_URL}/api/auth/validate") {
                bearerAuth(token)
            }
            
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            false
        }
    }
    
    fun close() {
        client.close()
    }
}