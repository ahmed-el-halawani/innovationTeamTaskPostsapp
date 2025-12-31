package com.halawany.innovationteamtaskpostsapp.core.data

import com.halawany.innovationteamtaskpostsapp.core.data.DataState.Error
import com.halawany.innovationteamtaskpostsapp.core.data.ExceptionMapper.toErrorState

sealed class DataState<out T> {

    data class Success<T>(val data: T) : DataState<T>()

    data class Error(
        val error: ErrorState,
        val throwable: Throwable? = null,
    ) : DataState<Nothing>()
}

suspend fun <T> safeCallSuspended(call: suspend () -> T): DataState<T> {
    return try {
        DataState.Success(call());
    } catch (e: Throwable) {
        e.toErrorDataState()
    }
}

fun <T> safeCall(call: () -> T): DataState<T> {
    return try {
        DataState.Success(call());
    } catch (e: Throwable) {
        e.toErrorDataState()
    }
}

fun Throwable.toErrorDataState(): DataState.Error =
    Error(this.toErrorState(), this)