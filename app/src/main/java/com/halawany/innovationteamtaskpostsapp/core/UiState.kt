package com.halawany.innovationteamtaskpostsapp.core

import com.halawany.innovationteamtaskpostsapp.core.data.ErrorState

sealed class UiState<out T> {

    object Idle : UiState<Nothing>()

    object Loading : UiState<Nothing>()

    data class Success<T>(
        val data: T,
    ) : UiState<T>()

    data class Error(
        val message: ErrorState,
    ) : UiState<Nothing>()
}