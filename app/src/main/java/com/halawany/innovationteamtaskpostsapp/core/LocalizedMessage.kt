package com.halawany.innovationteamtaskpostsapp.core

import android.content.Context
import androidx.annotation.StringRes

sealed class LocalizedMessage {
    data class Resource(
        @param:StringRes val resId: Int,
        val args: List<Any> = emptyList(),
    ) : LocalizedMessage()

    data class Dynamic(
        val value: String,
    ) : LocalizedMessage()

    object UnknownError : LocalizedMessage()


    fun resolve(context: Context): String =
        when (this) {
            is LocalizedMessage.Resource ->
                context.getString(resId, *args.toTypedArray())

            is LocalizedMessage.Dynamic -> value

            LocalizedMessage.UnknownError -> "Unknown Error"
        }
}