package com.halawany.innovationteamtaskpostsapp.presentation.post.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val refreshPostsUseCase: RefreshPostsUseCase,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {
    private val TAG = "PostListViewModel"


    private val _uiPostsState = MutableStateFlow<PagingData<Post>>(PagingData.empty())
    val uiPostsState: StateFlow<PagingData<Post>> = _uiPostsState.asStateFlow()

    private val _syncDataFromNetworkState = MutableLiveData<UiState<Unit>>(UiState.Idle)
    val syncDataFromNetworkState: LiveData<UiState<Unit>> = _syncDataFromNetworkState

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


    var onOnlineJob: Job? = null;
    fun syncNewsFromBackend() = viewModelScope.launch {
        onOnlineJob?.cancel()
        refreshPostsUseCase().collect {
            Log.e(TAG, "syncNewsFromBackend: $it")
            _syncDataFromNetworkState.value = it
            if (it is UiState.Error && it.message == ErrorState.NoInternet) {
                onOnlineJob = syncNewsFromBackendOnOnline()
            }
        }
    }


    fun syncNewsFromBackendOnOnline() = viewModelScope.launch {
        connectivityObserver.observe().collectLatest { connectivityObserverStatus ->
            if (connectivityObserverStatus == ConnectivityObserver.Status.Available) {
                if (isActive)
                    refreshPostsUseCase().collectLatest { _syncDataFromNetworkState.value = it }
            }
        }
    }


}