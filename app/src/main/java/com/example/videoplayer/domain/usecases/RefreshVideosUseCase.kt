package com.example.videoplayer.domain.usecases

import com.example.videoplayer.domain.repository.VideoRepository
import javax.inject.Inject

class RefreshVideosUseCase @Inject constructor(
    private val repository: VideoRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.refreshVideos()
    }
}