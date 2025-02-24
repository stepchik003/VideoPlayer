package com.example.videoplayer.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    val title: String,
    @SerializedName("poster") val thumbnail: String,
    val duration: String,
    val urls: Urls
):Parcelable

@Parcelize
data class Urls(
    val mp4: String
):Parcelable