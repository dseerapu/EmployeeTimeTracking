package com.example.aquaexchange.ui.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aquaexchange.datamanager.data_manager.DataManager
import javax.inject.Inject

@HiltWorker
class DailyWorker constructor(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    @Inject
    lateinit var dataManager: DataManager

    override suspend fun doWork(): Result {
        //TODO : Updates each day employee productive works
        val applicationContext = applicationContext
        return Result.success()
    }

}