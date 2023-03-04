package com.acquaexchange.base.logger

import android.util.Log
import timber.log.Timber

fun logMessage(message: String) {
    Timber.d(message)
}

fun logException(exception: Throwable) {
    Timber.e(exception)
}

class CrashReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        } else {
            // send crash to the firebase
        }
    }

}