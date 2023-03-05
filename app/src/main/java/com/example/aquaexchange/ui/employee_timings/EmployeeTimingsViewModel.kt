package com.example.aquaexchange.ui.employee_timings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.acquaexchange.base.BaseViewModel
import com.acquaexchange.base.logger.logMessage
import com.acquaexchange.base.utils.CoroutineUtils.asStateFlow
import com.aquaexchange.datamanager.data_manager.DataManager
import com.aquaexchange.datamanager.db.employee_day_stats.EmployeeDayStats
import com.aquaexchange.datamanager.db.employee_timings.EmployeeTimings
import com.aquaexchange.datamanager.utils.convertToDateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EmployeeTimingsViewModel @Inject constructor(
    private val dataManager: DataManager, saveStateHandle: SavedStateHandle
) : BaseViewModel(dataManager) {

    //EmployeeId from the previous screen
    private val employeeId: Int = saveStateHandle["employeeId"]!!

    private val dateSelected: Long = saveStateHandle["date"]!!

    private val dateSelectedInString = dateSelected.convertToDateFormat()

    val displayCheckInOrCheckOutButton = MutableStateFlow(false).apply {

        val currentDateInString = System.currentTimeMillis().convertToDateFormat()
        this.value = currentDateInString.equals(dateSelectedInString, true)
    }

    //Selected Date Flow
    private val selectedDateFlow = MutableStateFlow(System.currentTimeMillis())

    val selectedDateTextFlow = selectedDateFlow.map {
        it.convertToDateFormat()
    }.asStateFlow(viewModelScope, System.currentTimeMillis().convertToDateFormat())

    // Fetching all employee timings from `employeeTimings` table
    // These timings will be displayed to the employee in a recyclerview
    val employeeTimings =
        dataManager.getEmployeeTimings(employeeId, dateSelectedInString)

    // Fetching all employee timings from `employeeTimings` table
    // These timings will be displayed to the employee in a recyclerview
    private val employeeDayStatsFlow =
        dataManager.getEmployeeDayStatsAsAFlow(employeeId, dateSelectedInString).map {
            if (it.isNullOrEmpty()) null
            else it[0]
        }
            .asStateFlow(viewModelScope, null)

    //Employee details won't be null at anytime.
    private val employeeStatus = dataManager.getEmployeeDetailsAsAFlow(employeeId)!!

    // Employee Name to display
    val employeeName = employeeStatus.map {
        it.name
    }.asStateFlow(viewModelScope, "Default Employee")

    // Getting button State
    val buttonState = employeeDayStatsFlow.map { employee ->
        logMessage("button State")
        when {
            employee == null -> {
                logMessage("employee Day Stats are Null")
                CHECK_IN
            }
            employee.lastCheckIn == null -> {
                logMessage("employee Last Check IN is Null")
                CHECK_IN
            }
            employee.lastCheckOut == null -> {
                logMessage("employee Last Check OUT is Null")
                CHECK_OUT
            }
            else -> {
                employee.lastCheckOut!! <= employee.lastCheckIn!!
            }
        }

    }.asStateFlow(viewModelScope, false)

    val productiveTimeToDisplay = employeeDayStatsFlow.map {
        it?.productiveTimeInString ?: "-"
    }.asStateFlow(viewModelScope, "-")

    fun checkInOrCheckOutClicked() {

        val employeeDayStats = employeeDayStatsFlow.value ?: EmployeeDayStats(
            employeeId = employeeId,
            lastCheckIn = null,
            lastCheckOut = null,
            productiveTime = 0,
            dateInString = System.currentTimeMillis().convertToDateFormat(),
            date = Date(),
        )

        val buttonStateValue = buttonState.value

        if (buttonStateValue == CHECK_OUT) {

            val lastCheckInDate = employeeDayStats.lastCheckIn.convertToDateFormat()
            val currentDate = System.currentTimeMillis().convertToDateFormat()

            if (!lastCheckInDate.equals(currentDate, true) && !lastCheckInDate.equals("-", true)) {
                displayToast("You are trying to check out for another date")
            } else {
                val newItem = employeeDayStats.copy(lastCheckOut = System.currentTimeMillis())
                updateEmployeeDetails(buttonStateValue, newItem)
            }
        } else {

            val lastCheckOutDate = employeeDayStats.lastCheckOut.convertToDateFormat()
            val currentDate = System.currentTimeMillis().convertToDateFormat()

            if (!lastCheckOutDate.equals(currentDate, true) && !lastCheckOutDate.equals(
                    "-",
                    true
                )
            ) {
                displayToast("You are trying to check In for another date")
            } else {
                val newItem = employeeDayStats.copy(lastCheckIn = System.currentTimeMillis())
                updateEmployeeDetails(buttonStateValue, newItem)
            }
        }
    }

    /***
     * Updates employee details like checkin and checkout time fields in `employee` table
     * Adding employee timings in `employeeTimings` table
     * After above transactions are completed, callback triggers to [employeeStatus]
     * [buttonState] defines the state of the button
     */
    private fun updateEmployeeDetails(buttonState: Boolean, employeeDetails: EmployeeDayStats) {
        viewModelScope.launch(Dispatchers.IO) {

            //Generating productive Time
            generateProductiveTime(buttonState, employeeDetails)

            //updating employee details
            dataManager.insertEmployeeDayStats(employeeDetails)

            // Add Employee Timings Record
            addEmployeeTimingsRecord(buttonState, employeeDetails)
        }
    }

    private suspend fun addEmployeeTimingsRecord(
        buttonState: Boolean,
        employeeDetails: EmployeeDayStats
    ) {
        val checkType =
            if (buttonState == CHECK_OUT) EmployeeTimings.CheckType.CheckOut.toString()
            else EmployeeTimings.CheckType.CheckIn.toString()

        //Creating Employee Timings
        val employeeTimings = EmployeeTimings(
            employeeId = employeeDetails.employeeId,
            checkInTime = employeeDetails.lastCheckIn ?: 0,
            checkOutTime = employeeDetails.lastCheckOut ?: 0,
            checkType = checkType,
            dateInString = System.currentTimeMillis().convertToDateFormat(),
            date = Date()
        )

        //Adding employee check-in or checkout timing
        dataManager.addEmployeeTimings(employeeTimings)
    }

    private fun generateProductiveTime(
        buttonState: Boolean,
        employeeDetails: EmployeeDayStats
    ) {
        if (buttonState == CHECK_OUT) {
            employeeDetails.productiveTime +=
                employeeDetails.lastCheckOut!! - employeeDetails.lastCheckIn!!
            logMessage("productive Hours : \t ${employeeDetails.productiveTime}")
        }
    }

    fun onNewDateSelected(it: Long) {
        selectedDateFlow.value = it
    }

    companion object {
        private const val CHECK_OUT = true
        private const val CHECK_IN = false
    }
}