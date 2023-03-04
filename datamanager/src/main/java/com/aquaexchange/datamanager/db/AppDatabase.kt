package com.aquaexchange.datamanager.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aquaexchange.datamanager.db.converters.DateConverter
import com.aquaexchange.datamanager.db.employee.Employee
import com.aquaexchange.datamanager.db.employee.dao.EmployeeDao
import com.aquaexchange.datamanager.db.employee_day_stats.EmployeeDayStats
import com.aquaexchange.datamanager.db.employee_day_stats.dao.EmployeeDayStatsDao
import com.aquaexchange.datamanager.db.employee_timings.EmployeeTimings
import com.aquaexchange.datamanager.db.employee_timings.dao.EmployeeTimingsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Singleton


@Singleton
@Database(
    entities = [Employee::class, EmployeeTimings::class, EmployeeDayStats::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getEmployeeDao(): EmployeeDao

    abstract fun getEmployeeTimingsDao(): EmployeeTimingsDao

    abstract fun getEmployeeDayStatsDao(): EmployeeDayStatsDao

    suspend fun clearDatabase() {
        withContext(Dispatchers.IO) {
            clearAllTables()
        }
    }
}