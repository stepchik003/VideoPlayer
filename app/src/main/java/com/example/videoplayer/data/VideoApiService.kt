package com.example.videoplayer.data

import com.example.videoplayer.domain.models.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApiService {
    @GET("videos")
    suspend fun getVideos(
        @Query("api_key") apiKey: String,
        @Query("urls") urls: Boolean = true
    ): VideoResponse
}