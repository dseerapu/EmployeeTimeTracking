package com.aquaexchange.datamanager.db.employee.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aquaexchange.datamanager.db.employee.Employee
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract suspend fun insertEmployees(employeesList: List<Employee>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract suspend fun insertEmployee(employee: Employee)

    @Query("SELECT * FROM employee")
    internal abstract fun getEmployees(): Flow<List<Employee>>

    @Query("SELECT * FROM employee where id=:employeeId limit 1")
    internal abstract fun getEmployeeDetailsAsAFlow(employeeId: Int): Flow<Employee>?

    @Query("SELECT * FROM employee where id=:employeeId limit 1")
    internal abstract fun getEmployeeDetails(employeeId: Int): Employee?
}