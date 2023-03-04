package com.aquaexchange.datamanager.db.helper

import com.aquaexchange.datamanager.db.AppDatabase
import com.aquaexchange.datamanager.db.employee.Employee
import com.aquaexchange.datamanager.db.employee_day_stats.EmployeeDayStats
import com.aquaexchange.datamanager.db.employee_timings.EmployeeTimings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DBHelperImpl @Inject constructor(
    private val db: AppDatabase
) : DBHelper {

    override suspend fun insertEmployees(employeesList: List<Employee>) =
        db.getEmployeeDao().insertEmployees(employeesList)

    override suspend fun insertEmployee(employee: Employee) =
        db.getEmployeeDao().insertEmployee(employee)

    override fun getEmployees() = db.getEmployeeDao().getEmployees()

    override fun getEmployeeDetailsAsAFlow(employeeId: Int) =
        db.getEmployeeDao().getEmployeeDetailsAsAFlow(employeeId)

    override fun getEmployeeDetails(employeeId: Int): Employee? =
        db.getEmployeeDao().getEmployeeDetails(employeeId)


    override suspend fun addEmployeeTimingsList(employeeTimingsList: List<EmployeeTimings>) =
        db.getEmployeeTimingsDao().addEmployeeTimingsList(employeeTimingsList)

    override suspend fun addEmployeeTimings(employTimings: EmployeeTimings) =
        db.getEmployeeTimingsDao().addEmployeeTimings(employTimings)

    override fun getEmployeeTimings(employeeId: Int) =
        db.getEmployeeTimingsDao().getEmployeeTimings(employeeId)

    override fun getEmployeeTimings(employeeId: Int, selectedDateInString: String) =
        db.getEmployeeTimingsDao().getEmployeeTimings(employeeId, selectedDateInString)

    override fun getUniqueDates(employeeId: Int) =
        db.getEmployeeTimingsDao().getUniqueDates(employeeId)

    override fun getEmployeeDayStatsAsAFlow(
        employeeId: Int,
        selectedDateInString: String
    ) = db.getEmployeeDayStatsDao().getEmployeeDayStatsAsAFlow(employeeId, selectedDateInString)

    override suspend fun insertEmployeeDayStats(employeeDayStats: EmployeeDayStats) =
        db.getEmployeeDayStatsDao().insertEmployeeDayStats(employeeDayStats)

    override fun getEmployeeDayStats(employeeId: Int): Flow<List<EmployeeDayStats>> =
        db.getEmployeeDayStatsDao().getEmployeeDayStatsAsAFlow(employeeId)

    override suspend fun clearDatabase() {
        db.clearDatabase()
    }

}