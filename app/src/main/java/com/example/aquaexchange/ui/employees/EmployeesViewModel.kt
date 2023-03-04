package com.example.aquaexchange.ui.employees

import com.acquaexchange.base.BaseViewModel
import com.acquaexchange.base.utils.ConflatedChannel
import com.acquaexchange.base.utils.sendValue
import com.aquaexchange.datamanager.data_manager.DataManager
import com.aquaexchange.datamanager.db.employee.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    val dataManager: DataManager
) : BaseViewModel(dataManager) {

    val navigateToEmployeeTimingsScreen = ConflatedChannel<Pair<Int,Long>>()

    fun onNewDateSelected(employeeID: Int, date: Long) {
        navigateToEmployeeTimingsScreen.sendValue(Pair(employeeID,date))
    }

    val data = dataManager.getEmployees()
}