package com.example.videoplayer.data

import com.example.videoplayer.domain.models.Video
import com.example.videoplayer.domain.repository.VideoRepository
import com.google.gson.JsonSyntaxException
import javax.inject.Inject
import javax.inject.Named

class VideoRepositoryImpl @Inject constructor(
    private val apiService: VideoApiService,
    @Named("apiKey") private val apiKey: String,
    private val videoDao: VideoDao
) : VideoRepository{
    override suspend fun getVideos(): List<Video> {
        return videoDao.getVideos().map { it.toVideo() }
    }

    override suspend fun refreshVideos(): Result<Unit> {
        return try {
            val apiVideos = apiService.getVideos(apiKey).hits
            if (apiVideos.isNotEmpty()) videoDao.deleteAllVideos()
            videoDao.insertVideos(apiVideos.map { VideoEntity.fromVideo(it) })
            Result.success(Unit)
        } catch (e: JsonSyntaxException) {
            Result.failure(Exception(message = "Invalid JSON response"))
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}