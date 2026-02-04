package com.demo.streamflix.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.streamflix.data.repository.AuthRepository
import com.demo.streamflix.util.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    sealed class ValidationState {
        object Loading : ValidationState()
        object Valid : ValidationState()
        object Invalid : ValidationState()
        object Expired : ValidationState()
    }

    private val _validationState = MutableStateFlow<ValidationState>(ValidationState.Loading)
    val validationState: StateFlow<ValidationState> = _validationState.asStateFlow()

    fun validateMembership() {
        viewModelScope.launch {
            try {
                // Check if user is logged in
                val isLoggedIn = sharedPrefs.getBoolean("is_logged_in", false)
                val token = sharedPrefs.getString("auth_token", null)

                if (!isLoggedIn || token == null) {
                    _validationState.value = ValidationState.Invalid
                    return@launch
                }

                // Validate membership via API
                val isValid = authRepository.validateMembership(token)

                if (isValid) {
                    _validationState.value = ValidationState.Valid
                } else {
                    _validationState.value = ValidationState.Expired
                }
            } catch (e: Exception) {
                // If validation fails, assume invalid
                _validationState.value = ValidationState.Invalid
            }
        }
    }
}