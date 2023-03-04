package com.example.aquaexchange.ui.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.acquaexchange.base.logger.CrashReportingTree
import com.example.aquaexchange.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class EmployeeTimingsManagementApp : Application(), Configuration.Provider {

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // Display all logs in debug mode
            Timber.plant(Timber.DebugTree())
        } else {
            // Records crashes and sends log to Firebase crashlytics
            Timber.plant(CrashReportingTree())
        }
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}