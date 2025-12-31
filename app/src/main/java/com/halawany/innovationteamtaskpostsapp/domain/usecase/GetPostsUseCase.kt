package com.halawany.innovationteamtaskpostsapp.domain.usecase

import androidx.paging.PagingData
import com.halawany.innovationteamtaskpostsapp.domain.model.Post
import com.halawany.innovationteamtaskpostsapp.domain.repository.PostRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase @Inject constructor(private val repository: PostRepository) {
    operator fun invoke(): Flow<PagingData<Post>> {
        return repository.getPosts()
    }
}
