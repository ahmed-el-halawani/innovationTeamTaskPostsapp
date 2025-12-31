package com.halawany.innovationteamtaskpostsapp.domain.model

data class Post(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val link: String?,
    val source: String?,
    val publishedAt: String?,
    val author: String
)
