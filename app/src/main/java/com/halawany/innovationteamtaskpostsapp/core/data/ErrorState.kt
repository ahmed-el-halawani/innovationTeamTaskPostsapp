package com.halawany.innovationteamtaskpostsapp.core.data

sealed class ErrorState {

    // Network
    object NoInternet : ErrorState()
    object Timeout : ErrorState()
    object ConnectionFailed : ErrorState()

    // HTTP
    data class Http(
        val code: Int,
        val message: String? = null,
    ) : ErrorState()

    // Backend (business errors)
    data class Api(
        val code: String,
        val message: String? = null,
    ) : ErrorState()

    // Parsing / mapping
    object Serialization : ErrorState()

    // database issue
    object NotFound : ErrorState()

    // Fallback
    object Unknown : ErrorState()
}
