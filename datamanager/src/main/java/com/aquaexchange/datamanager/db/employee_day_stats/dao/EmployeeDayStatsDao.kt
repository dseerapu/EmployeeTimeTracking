package com.aquaexchange.datamanager.db.employee_day_stats.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aquaexchange.datamanager.db.employee_day_stats.EmployeeDayStats
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EmployeeDayStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract suspend fun insertEmployeeDayStatsList(employeeStatsList: List<EmployeeDayStats>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract suspend fun insertEmployeeDayStats(employeeDayStats: EmployeeDayStats)

    @Query("SELECT * FROM employeeDayStats WHERE employeeId=:employeeId and dateInString=:selectedDateInString")
    internal abstract fun getEmployeeDayStatsAsAFlow(
        employeeId: Int,
        selectedDateInString: String
    ): Flow<List<EmployeeDayStats>?>

    @Query("SELECT * FROM employeeDayStats WHERE employeeId=:employeeId")
    internal abstract fun getEmployeeDayStatsAsAFlow(
        employeeId: Int,
    ): Flow<List<EmployeeDayStats>>
}