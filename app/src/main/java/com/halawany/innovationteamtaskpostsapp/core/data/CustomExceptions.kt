package com.halawany.innovationteamtaskpostsapp.core.data

data class CustomExceptions(val errorState: ErrorState, val throwable: Throwable? = null) : Exception() {
}