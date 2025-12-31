package com.halawany.innovationteamtaskpostsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.halawany.innovationteamtaskpostsapp.core.data.CustomExceptions
import com.halawany.innovationteamtaskpostsapp.core.data.DataState
import com.halawany.innovationteamtaskpostsapp.core.data.ErrorState
import com.halawany.innovationteamtaskpostsapp.core.data.safeCall
import com.halawany.innovationteamtaskpostsapp.core.data.safeCallSuspended
import com.halawany.innovationteamtaskpostsapp.data.local.PostDatabase
import com.halawany.innovationteamtaskpostsapp.data.mappers.toDomain
import com.halawany.innovationteamtaskpostsapp.data.mappers.toEntity
import com.halawany.innovationteamtaskpostsapp.data.remote.PostApiService
import com.halawany.innovationteamtaskpostsapp.domain.model.Post
import com.halawany.innovationteamtaskpostsapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val api: PostApiService, private val db: PostDatabase) : PostRepository {
    private val dao = db.dao

    override fun getPosts(): Flow<PagingData<Post>> {
        return Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = false), pagingSourceFactory = { dao.getPosts() })
            .flow
            .map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override fun getPost(id: String): DataState<Post> {
        return safeCall {
            val postDetails = dao.getPostById(id) ?: throw CustomExceptions(ErrorState.NotFound)
            postDetails.toDomain()
        }
    }

    override suspend fun refreshPosts(): DataState<Unit> {
        return safeCallSuspended {
            val response = api.getPosts()
            val entities = response.data.map { it.toEntity() }
            dao.clearPosts()
            dao.insertPosts(entities)
        }
    }
}
