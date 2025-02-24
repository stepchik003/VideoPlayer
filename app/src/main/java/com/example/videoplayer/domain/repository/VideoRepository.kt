package com.example.videoplayer.domain.repository

import com.example.videoplayer.domain.models.Video

interface VideoRepository {
    suspend fun getVideos(): List<Video>
    suspend fun refreshVideos(): Result<Unit>
}