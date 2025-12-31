package com.halawany.innovationteamtaskpostsapp.domain.repository

import androidx.paging.PagingData
import com.halawany.innovationteamtaskpostsapp.core.data.DataState
import com.halawany.innovationteamtaskpostsapp.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<PagingData<Post>>
    fun getPost(id: String): DataState<Post>
    suspend fun refreshPosts(): DataState<Unit>
}
