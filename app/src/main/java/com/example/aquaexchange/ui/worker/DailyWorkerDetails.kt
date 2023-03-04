package com.example.aquaexchange.ui.worker

import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkRequest
import java.util.*
import java.util.concurrent.TimeUnit

object DailyWorkerDetails {

    fun getWorkRequest(): WorkRequest {
        // Create a OneTimeWorkRequest object that specifies the worker to run
        return OneTimeWorkRequestBuilder<DailyWorker>()
            .setConstraints(constraints)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()
    }

    // Create a Constraints object that specifies the conditions that must be met for the worker to run
    private val constraints = Constraints.Builder()
        //Add network constraints if any
        .build()

    // Calculate the initial delay to run the worker at 11:59pm today
    private fun calculateInitialDelay(): Long {
        val currentTime = Calendar.getInstance().timeInMillis
        val targetTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        return if (targetTime <= currentTime) {
            targetTime + TimeUnit.DAYS.toMillis(1) - currentTime
        } else {
            targetTime - currentTime
        }
    }

}