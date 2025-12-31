package com.halawany.innovationteamtaskpostsapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NewsResponseDto(val status: String, val data: List<PostDto>)

data class PostDto(
    val title: String,
    val link: String?,
    val authors: List<String> = emptyList(),
    @SerializedName("photo_url") val photoUrl: String?,
    @SerializedName("source_logo_url") val sourceLogoUrl: String?,
    @SerializedName("published_datetime_utc") val publishedAt: String?,
    val snippet: String?,
)
