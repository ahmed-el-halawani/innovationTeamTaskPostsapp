package com.halawany.innovationteamtaskpostsapp.domain.usecase

import com.halawany.innovationteamtaskpostsapp.core.UiState
import com.halawany.innovationteamtaskpostsapp.core.data.DataState
import com.halawany.innovationteamtaskpostsapp.domain.model.Post
import com.halawany.innovationteamtaskpostsapp.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPostDetailsUseCase @Inject constructor(private val repository: PostRepository) {
    operator fun invoke(id: String) = flow<UiState<Post>> {
        emit(UiState.Loading)
        when (val it = repository.getPost(id)) {
            is DataState.Error -> emit(UiState.Error(it.error))
            is DataState.Success -> emit(UiState.Success(it.data))
        }
    }.flowOn(Dispatchers.IO)
}
