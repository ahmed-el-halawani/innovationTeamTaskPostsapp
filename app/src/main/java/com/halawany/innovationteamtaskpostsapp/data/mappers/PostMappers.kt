package com.halawany.innovationteamtaskpostsapp.data.mappers

import com.halawany.innovationteamtaskpostsapp.data.local.entity.PostEntity
import com.halawany.innovationteamtaskpostsapp.data.remote.dto.PostDto
import com.halawany.innovationteamtaskpostsapp.domain.model.Post

fun PostEntity.toDomain(): Post {
    return Post(
        id = id,
        title = title,
        imageUrl = imageUrl,
        link = link,
        author = author,
        source = source,
        publishedAt = publishedAt
    )
}

fun PostDto.toEntity(): PostEntity {
    return PostEntity(
        id = link ?: title.hashCode().toString(),
        title = title,
        imageUrl = photoUrl,
        author = authors.firstOrNull() ?: "Unknown",
        link = link,
        source = sourceLogoUrl,
        publishedAt = publishedAt,
        description = snippet ?: ""
    )
}
