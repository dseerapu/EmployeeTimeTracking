package com.aquaexchange.datamanager.db.employee_timings.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aquaexchange.datamanager.db.employee_timings.EmployeeTimings
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class EmployeeTimingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract suspend fun addEmployeeTimingsList(employeeTimingsList: List<EmployeeTimings>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract suspend fun addEmployeeTimings(employTimings: EmployeeTimings)

    @Query("SELECT * FROM employeeTimings WHERE employeeId=:employeeId")
    internal abstract fun getEmployeeTimings(employeeId: Int): Flow<List<EmployeeTimings>>

    @Query("SELECT * FROM employeeTimings WHERE employeeId=:employeeId and dateInString=:selectedDateInString")
    internal abstract fun getEmployeeTimings(employeeId: Int, selectedDateInString :String): Flow<List<EmployeeTimings>>

    @Query("SELECT DISTINCT dateInString from employeeTimings WHERE employeeId=:employeeId order by date desc")
    internal abstract fun getUniqueDates(
        employeeId: Int,
    ): Flow<List<String>>
}