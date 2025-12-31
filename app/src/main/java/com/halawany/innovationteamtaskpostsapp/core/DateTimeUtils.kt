package com.halawany.innovationteamtaskpostsapp.core

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateTimeUtils {
    fun formatShort(isoDate: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        val date = sdf.parse(isoDate)
        return if (date != null) formatter.format(date) else ""
    }
}