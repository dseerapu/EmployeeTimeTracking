package com.aquaexchange.datamanager.db.helper

import com.acquaexchange.base.BaseDataManager
import com.aquaexchange.datamanager.db.employee.Employee
import com.aquaexchange.datamanager.db.employee_day_stats.EmployeeDayStats
import com.aquaexchange.datamanager.db.employee_timings.EmployeeTimings
import kotlinx.coroutines.flow.Flow

interface DBHelper : BaseDataManager {

    //Inserting all employees into `employee` table
    suspend fun insertEmployees(employeesList: List<Employee>)

    //Inserting one employee into `employee` table
    suspend fun insertEmployee(employee: Employee)

    // Fetching all employees from `employee` table
    fun getEmployees(): Flow<List<Employee>>

    // Fetch one employee information using employeeId
    fun getEmployeeDetailsAsAFlow(employeeId: Int): Flow<Employee>?

    // Fetch one employee information using employeeId
    fun getEmployeeDetails(employeeId: Int): Employee?

    //Inserting employee timings list into `employeeTimings` table
    suspend fun addEmployeeTimingsList(employeeTimingsList: List<EmployeeTimings>)

    //Inserting one employee timing into `employeeTimings` table
    suspend fun addEmployeeTimings(employTimings: EmployeeTimings)

    //fetching employee related timings in day
    fun getEmployeeTimings(employeeId: Int): Flow<List<EmployeeTimings>>

    //fetching employee related timings in day
    fun getEmployeeTimings(employeeId: Int, selectedDateInString: String): Flow<List<EmployeeTimings>>

    fun getUniqueDates(employeeId: Int): Flow<List<String>>

    suspend fun insertEmployeeDayStats(employeeDayStats: EmployeeDayStats)

    fun getEmployeeDayStatsAsAFlow(employeeId: Int, selectedDateInString: String): Flow<List<EmployeeDayStats>?>

    fun getEmployeeDayStats(employeeId: Int) : Flow<List<EmployeeDayStats>>

}