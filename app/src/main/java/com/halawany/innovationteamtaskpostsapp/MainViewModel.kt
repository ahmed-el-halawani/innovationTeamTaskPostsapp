package com.halawany.innovationteamtaskpostsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.halawany.innovationteamtaskpostsapp.core.network.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val connectivityObserver: ConnectivityObserver) : ViewModel() {
    fun connectivityObserverLiveData()  =
        connectivityObserver.observe().asLiveData(viewModelScope.coroutineContext)

}