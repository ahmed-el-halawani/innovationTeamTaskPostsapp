package com.halawany.innovationteamtaskpostsapp.presentation.post.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.halawany.innovationteamtaskpostsapp.core.UiState
import com.halawany.innovationteamtaskpostsapp.core.data.ErrorState
import com.halawany.innovationteamtaskpostsapp.core.network.ConnectivityObserver
import com.halawany.innovationteamtaskpostsapp.domain.model.Post
import com.halawany.innovationteamtaskpostsapp.domain.usecase.GetPostsUseCase
import com.halawany.innovationteamtaskpostsapp.domain.usecase.RefreshPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val refreshPostsUseCase: RefreshPostsUseCase,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {


    private val _uiPostsState = MutableStateFlow<PagingData<Post>>(PagingData.empty())
    val uiPostsState: StateFlow<PagingData<Post>> = _uiPostsState.asStateFlow()

    private val _syncDataFromNetworkState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val syncDataFromNetworkState: StateFlow<UiState<Unit>> = _syncDataFromNetworkState.asStateFlow()

    init {
        getPosts()
        syncNewsFromBackend()
    }

    fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase().cachedIn(viewModelScope).collectLatest {
                _uiPostsState.value = it
            }
        }
    }


    fun syncNewsFromBackend() = viewModelScope.launch {
        refreshPostsUseCase().collectLatest {
            _syncDataFromNetworkState.value = it
            if (it is UiState.Error && it.message == ErrorState.NoInternet) {
                syncNewsFromBackendOnOnline()
            }
        }
    }


    suspend fun syncNewsFromBackendOnOnline() {
        connectivityObserver.observe().collectLatest { connectivityObserverStatus ->
            if (connectivityObserverStatus == ConnectivityObserver.Status.Available) {
                refreshPostsUseCase().collectLatest { _syncDataFromNetworkState.value = it }
            }
        }
    }


}