package com.demo.streamflix.ui.player

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
class PlayerViewModel @Inject constructor(
    private val channelRepository: ChannelRepository
) : ViewModel() {

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun checkIfFavorite(channelId: Int) {
        viewModelScope.launch {
            try {
                val isFav = channelRepository.isChannelFavorite(channelId)
                _isFavorite.value = isFav
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to check favorite status"
            }
        }
    }

    fun toggleFavorite(channel: com.demo.streamflix.data.model.Channel) {
        viewModelScope.launch {
            try {
                val currentStatus = _isFavorite.value
                if (currentStatus) {
                    channelRepository.removeFromFavorites(channel.id)
                } else {
                    channelRepository.addToFavorites(channel)
                }
                _isFavorite.value = !currentStatus
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to toggle favorite"
            }
        }
    }
}