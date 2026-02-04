package com.demo.streamflix.ui.search

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
class SearchViewModel @Inject constructor(
    private val channelRepository: ChannelRepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<com.demo.streamflix.data.model.Channel>>(emptyList())
    val searchResults: StateFlow<List<com.demo.streamflix.data.model.Channel>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun searchChannels(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = channelRepository.searchChannels(query)
                when (result) {
                    is com.demo.streamflix.util.NetworkResult.Success -> {
                        _searchResults.value = result.data ?: emptyList()
                    }
                    is com.demo.streamflix.util.NetworkResult.Error -> {
                        _error.value = result.message
                        _searchResults.value = emptyList()
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Search failed"
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}