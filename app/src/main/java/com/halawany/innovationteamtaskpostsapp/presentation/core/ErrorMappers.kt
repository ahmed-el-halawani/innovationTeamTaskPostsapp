package com.halawany.innovationteamtaskpostsapp.presentation.core

import android.content.Context
import com.halawany.innovationteamtaskpostsapp.R
import com.halawany.innovationteamtaskpostsapp.core.LocalizedMessage
import com.halawany.innovationteamtaskpostsapp.core.data.ErrorState

object ErrorMappers {

    fun ErrorState.toLocalizedMessage(context: Context): String =
        when (this) {
            //  Network
            ErrorState.NoInternet ->
                LocalizedMessage.Resource(R.string.error_no_internet)

            ErrorState.Timeout ->
                LocalizedMessage.Resource(R.string.error_timeout)

            ErrorState.ConnectionFailed ->
                LocalizedMessage.Resource(R.string.error_connection_failed)

            //  HTTP
            is ErrorState.Http -> when (code) {
                401 -> LocalizedMessage.Resource(R.string.error_unauthorized)
                403 -> LocalizedMessage.Resource(R.string.error_forbidden)
                404 -> LocalizedMessage.Resource(R.string.error_not_found)
                409 -> LocalizedMessage.Resource(R.string.error_conflict)
                in 500..599 ->
                    LocalizedMessage.Resource(R.string.error_server)

                else ->
                    LocalizedMessage.Resource(
                        R.string.error_http_generic,
                        listOf(code)
                    )
            }

            //  Backend business error
            is ErrorState.Api ->
                LocalizedMessage.Dynamic(
                    message ?: "Unknown server error"
                )

            //  Serialization
            ErrorState.Serialization ->
                LocalizedMessage.Resource(R.string.error_parsing)

            is ErrorState.Unknown ->
                LocalizedMessage.Resource(R.string.error_unknown)

            is ErrorState.NotFound ->
                LocalizedMessage.Resource(R.string.error_not_found)

        }.resolve(context)

}