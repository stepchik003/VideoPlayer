package com.example.videoplayer.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.videoplayer.domain.models.Urls
import com.example.videoplayer.domain.models.Video

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val url: String,
    val title: String,
    val thumbnail: String,
    val duration: String
) {
    companion object {
        fun fromVideo(video: Video): VideoEntity {
            return VideoEntity(
                url = video.urls.mp4,
                title = video.title,
                thumbnail = video.thumbnail,
                duration = video.duration
            )
        }
    }

    fun toVideo(): Video {
        return Video(title, thumbnail, duration, Urls(url))
    }
}
