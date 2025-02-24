package com.example.videoplayer.presentation

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videoplayer.R
import com.example.videoplayer.databinding.VideoItemBinding
import com.example.videoplayer.domain.models.Video

class VideoViewHolder(
    private val binding: VideoItemBinding,
    private val onItemClick: (Video) -> Unit
) : RecyclerView.ViewHolder(binding.root){
    fun bind(video: Video) {
        binding.titleTextView.text = video.title
        binding.durationTextView.text = time(video.duration)

        Glide.with(binding.root)
            .load(video.thumbnail)
            .error(R.drawable.video_placeholder)
            .into(binding.thumbnailImageView)

        binding.root.setOnClickListener {
            onItemClick(video)
        }
    }

    private fun time(raw: String): String {
        val seconds = raw.split(".")[0].toInt()
        return if (seconds < 60) {
            "00:$seconds"
        }
        else {
            "${seconds/60}:${seconds%60}"
        }
    }
}