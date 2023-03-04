package com.acquaexchange.base.utils

import android.content.Context

fun Context.isAutomaticDateTimeEnabled(): Boolean {
    return android.provider.Settings.Global.getInt(
        contentResolver, android.provider.Settings.Global.AUTO_TIME, 0
    ) == 1 &&
            android.provider.Settings.Global.getInt(
                contentResolver, android.provider.Settings.Global.AUTO_TIME_ZONE, 0
            ) == 1
}