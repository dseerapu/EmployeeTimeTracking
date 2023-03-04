package com.aquaexchange.datamanager.utils

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

val SIMPLE_DATE_FORMAT_WITH_AM_PM = SimpleDateFormat("hh:mm: ss a", Locale.getDefault())
val SIMPLE_DATE_FORMAT = SimpleDateFormat("hh:mm: ss a", Locale.getDefault())
val D_MMM = SimpleDateFormat("d MMM", Locale.getDefault())

fun Long?.displayTimeInString(): String {
    if (this == null || this == 0L) return "-"
    val formatter = SIMPLE_DATE_FORMAT_WITH_AM_PM
    val date = Date(this)
    return formatter.format(date)
}

fun Long?.convertToDateFormat(): String {
    if (this == null || this == 0L) return "-"
    val formatter = D_MMM
    val date = Date(this)
    return formatter.format(date)
}

fun Long.convertToHoursAndMinutes(): String {
    if (this == 0L) return "-"

    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) % 60
    return "$hours h $minutes m $seconds s"
}

@BindingAdapter("isVisible")
fun View.isVisible(boolean: Boolean) {
    isVisible = boolean
}



