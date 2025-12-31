package com.halawany.innovationteamtaskpostsapp.core.data

import com.google.gson.stream.MalformedJsonException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionMapper {

    fun Throwable.toErrorState(): ErrorState {
        return when (this) {
            //  Network
            is UnknownHostException -> ErrorState.NoInternet

            is ConnectException -> ErrorState.ConnectionFailed

            is SocketTimeoutException -> ErrorState.Timeout

            //  HTTP
            is HttpException -> ErrorState.Http(code = code(), message = response()?.errorBody()?.string())

            //  Serialization
            is MalformedJsonException -> ErrorState.Serialization

            // custom Issues
            is CustomExceptions -> errorState

            else -> ErrorState.Unknown
        }
    }

}