package com.demo.streamflix.data.repository

import com.demo.streamflix.data.model.request.LoginRequest
import com.demo.streamflix.data.network.ApiService
import com.demo.streamflix.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun login(loginRequest: LoginRequest): NetworkResult<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(loginRequest)
                if (response.isSuccessful && response.body() != null) {
                    NetworkResult.Success(Unit)
                } else {
                    NetworkResult.Error(
                        response.errorBody()?.string() ?: "Login failed"
                    )
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Network error")
            }
        }
    }

    suspend fun logout(): NetworkResult<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.logout()
                if (response.isSuccessful && response.body() != null) {
                    NetworkResult.Success(Unit)
                } else {
                    NetworkResult.Error("Logout failed")
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Network error")
            }
        }
    }

    suspend fun validateMembership(token: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.validateToken(token)
                response.isSuccessful && response.body()?.data == true
            } catch (e: Exception) {
                false
            }
        }
    }
}