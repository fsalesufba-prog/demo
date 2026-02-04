package com.demo.streamflix.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.streamflix.data.model.request.LoginRequest
import com.demo.streamflix.data.repository.AuthRepository
import com.demo.streamflix.util.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val message: String) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = authRepository.login(loginRequest)
                
                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!
                    
                    // Save token and user data
                    sharedPrefs.putString("auth_token", authResponse.token)
                    sharedPrefs.putBoolean("is_logged_in", true)
                    sharedPrefs.putString("user_email", loginRequest.email)
                    
                    // Save remember me preference
                    if (loginRequest.rememberMe) {
                        sharedPrefs.putBoolean("remember_me", true)
                    }
                    
                    _loginState.value = LoginState.Success("Login successful")
                } else {
                    _loginState.value = LoginState.Error(
                        response.errorBody()?.string() ?: "Login failed"
                    )
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(
                    e.message ?: "Network error"
                )
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}