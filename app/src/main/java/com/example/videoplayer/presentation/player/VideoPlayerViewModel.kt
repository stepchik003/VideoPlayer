package com.example.videoplayer.presentation.player

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor() : ViewModel() {
    private var exoPlayer: ExoPlayer? = null
    private var currentPos: Long = 0
    private var videoUrl: String? = null


    private fun initializePlayer(context: Context, videoUrl: String) {
        exoPlayer = ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            seekTo(currentPos)
        }
    }

    fun initializeVideo(context: Context, url: String) {
        if (url != videoUrl) {
            videoUrl = url
            initializePlayer(context, url)
        }
    }

    fun bindPlayerView(playerView: PlayerView) {
        playerView.player = exoPlayer
    }

    private fun releasePlayer() {
        exoPlayer?.release()
        exoPlayer = null
    }

    fun savePosition() {
        currentPos = exoPlayer?.currentPosition ?: 0
    }

    override fun onCleared() {
        super.onCleared()
        releasePlayer()
    }

}