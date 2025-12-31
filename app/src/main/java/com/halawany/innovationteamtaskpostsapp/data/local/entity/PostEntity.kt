package com.halawany.innovationteamtaskpostsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val title: String,
    val imageUrl: String?,
    val link: String?,
    val source: String?,
    val publishedAt: String?,
    val description: String,
    val author: String
)

