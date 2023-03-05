package com.example.aquaexchange.ui.create_employee

import androidx.lifecycle.viewModelScope
import com.acquaexchange.base.BaseViewModel
import com.acquaexchange.base.utils.ConflatedChannel
import com.aquaexchange.datamanager.data_manager.DataManager
import com.aquaexchange.datamanager.db.employee.Employee
import com.aquaexchange.datamanager.db.employee_day_stats.EmployeeDayStats
import com.aquaexchange.datamanager.utils.convertToDateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateEmployeeViewModel @Inject constructor(
    private val dataManager: DataManager
) : BaseViewModel(dataManager) {

    //Employee Name Flow
    val employeeNameFlow = MutableStateFlow("")

    //Employee ID Flow
    val employeeIdFlow = MutableStateFlow("")
    val employeeIdErrorFlow = MutableStateFlow("")

    //Channel to inform and dismiss create employee bottom sheet dialog
    val closeCreateEmployeeDialog = ConflatedChannel<Unit>()

    fun addEmployee() {
        val employeeName = employeeNameFlow.value
        val employeeID = employeeIdFlow.value
        viewModelScope.launch(Dispatchers.IO) {

            //Fetching the employee details if existing with user entered employeeID
            val existingEmployee = dataManager.getEmployeeDetails(employeeID.toInt())


            if (existingEmployee != null) {
                // If employee is already existing, Displaying a toast.
                employeeIdErrorFlow.value = "This ID is already present in our database"
                displayToast("This ID is already present in our database")
            } else {
                dataManager.insertEmployee(
                    Employee(
                        id = employeeID.toInt(),
                        name = employeeName
                    )
                )

                dataManager.insertEmployeeDayStats(
                    EmployeeDayStats(
                        employeeId = employeeID.toInt(),
                        lastCheckIn = null,
                        lastCheckOut = null,
                        productiveTime = 0,
                        dateInString = System.currentTimeMillis().convertToDateFormat(),
                        date = Date(),
                    )
                )

                //Calls closeCreateEmployeeDialog only once
                closeCreateEmployeeDialog.send(Unit)
            }
        }
    }
}