package com.example.aquaexchange

import com.aquaexchange.datamanager.db.employee.Employee
import com.aquaexchange.datamanager.db.employee_timings.EmployeeTimings
import java.util.*

object EmployeeDetailsData {

    fun getEmployees(): List<Employee> {

        //creating empty employees list
        val employeesList = ArrayList<Employee>()

        for (index in 1..10) {

            //Adding employee details to employee list
            employeesList.add(
                Employee(
                    index, "Employee $index"
                )
            )
        }

        //return employees list after adding employee details
        return employeesList
    }

    fun getEmployeesTimingsList(): List<EmployeeTimings> {

        //creating empty employees list
        val employeeTimingsList = ArrayList<EmployeeTimings>()

        for (index in 1..10) {

            //Adding employee details to employee list
            employeeTimingsList.add(
                EmployeeTimings(
                    employeeId = 4,
                    checkInTime = System.currentTimeMillis(),
                    checkOutTime = System.currentTimeMillis() + 100,
                    checkType = EmployeeTimings.CheckType.CheckIn.toString(),
                    date = Date(),
                    dateInString = System.currentTimeMillis().toString()
                )
            )
        }

        //return employee timings in a day
        return employeeTimingsList
    }
}


