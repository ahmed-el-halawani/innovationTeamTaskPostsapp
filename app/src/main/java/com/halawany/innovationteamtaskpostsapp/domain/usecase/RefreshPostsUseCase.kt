package com.halawany.innovationteamtaskpostsapp.domain.usecase

import com.halawany.innovationteamtaskpostsapp.core.UiState
import com.halawany.innovationteamtaskpostsapp.core.data.DataState
import com.halawany.innovationteamtaskpostsapp.core.data.ErrorState
import com.halawany.innovationteamtaskpostsapp.core.network.NetworkMonitor
import com.halawany.innovationteamtaskpostsapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshPostsUseCase @Inject constructor(private val repository: PostRepository, private val networkMonitor: NetworkMonitor) {
    operator fun invoke() = flow {
        emit(UiState.Loading)
        if (!networkMonitor.isConnected()) {
            emit(UiState.Error(ErrorState.NoInternet))
            return@flow
        }

        when (val response = repository.refreshPosts()) {
            is DataState.Error -> emit(UiState.Error(response.error))
            is DataState.Success<*> -> emit(UiState.Success(Unit))
        }
    }
}
