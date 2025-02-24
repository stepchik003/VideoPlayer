package com.example.videoplayer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer.domain.models.Video
import com.example.videoplayer.domain.usecases.GetVideosUseCase
import com.example.videoplayer.domain.usecases.RefreshVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase,
    private val refreshVideosUseCase: RefreshVideosUseCase
) : ViewModel() {
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> get() = _videos

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    private val _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?> get() = _error

    fun loadVideos() {
        viewModelScope.launch {
            _videos.value = getVideosUseCase()
        }
    }

    fun refreshVideos() {
        viewModelScope.launch {
            _isRefreshing.value = true
            val result = refreshVideosUseCase()
            _isRefreshing.value = false

            result.onSuccess {
                loadVideos()
            }.onFailure {
                _error.value = it
            }
        }
    }
}