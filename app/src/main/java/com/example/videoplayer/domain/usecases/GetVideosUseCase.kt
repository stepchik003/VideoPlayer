package com.example.videoplayer.domain.usecases

import com.example.videoplayer.domain.models.Video
import com.example.videoplayer.domain.repository.VideoRepository
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(private val repository: VideoRepository) {
    suspend operator fun invoke(): List<Video> = repository.getVideos()
}