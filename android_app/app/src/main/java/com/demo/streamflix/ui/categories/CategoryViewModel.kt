package com.demo.streamflix.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.streamflix.data.repository.ChannelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val channelRepository: ChannelRepository
) : ViewModel() {

    private val _channels = MutableStateFlow<List<com.demo.streamflix.data.model.Channel>>(emptyList())
    val channels: StateFlow<List<com.demo.streamflix.data.model.Channel>> = _channels.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadChannelsByCategory(categoryId: Int, refresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = channelRepository.getChannelsByCategory(categoryId)
                    .collect { networkResult ->
                        when (networkResult) {
                            is com.demo.streamflix.util.NetworkResult.Success -> {
                                _channels.value = networkResult.data ?: emptyList()
                            }
                            is com.demo.streamflix.util.NetworkResult.Error -> {
                                _error.value = networkResult.message
                            }
                            else -> {}
                        }
                    }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load channels"
            } finally {
                _isLoading.value = false
            }
        }
    }
}