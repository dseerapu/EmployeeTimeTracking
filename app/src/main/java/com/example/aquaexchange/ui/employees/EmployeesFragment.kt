package com.example.aquaexchange.ui.employees

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.acquaexchange.base.BaseFragment
import com.acquaexchange.base.utils.showDatePickerDialog
import com.aquaexchange.datamanager.db.employee.Employee
import com.example.aquaexchange.R
import com.example.aquaexchange.databinding.FragmentEmployeesBinding
import com.example.aquaexchange.ui.employees.adapter.EmployeesAdapter
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class EmployeesFragment : BaseFragment<EmployeesViewModel, FragmentEmployeesBinding>() {

    private val adapter by lazy {
        EmployeesAdapter(::openDateSelectFilterItem)
    }

    private val employeeTimingsViewModel: EmployeesViewModel by viewModels()

    override val TAG = "EmployeeTimingsScreen"

    override fun getLayoutResource() = R.layout.fragment_employees

    override fun initObservers(viewLifecycleOwner: LifecycleOwner) {
        lifecycleScope.launchWhenResumed {
            vm.data.collectLatest(adapter::submitList)
        }

        lifecycleScope.launchWhenResumed {
            val data = vm.navigateToEmployeeTimingsScreen.receive()
            onEmployeeClicked(data)
        }
    }

    override fun setUp() {
        dataBinding.recyclerView.adapter = adapter

        dataBinding.fab.setOnClickListener {
            findNavController().navigate(EmployeesFragmentDirections.navigateToCreateEmployeeScreen())
        }
    }

    override fun getViewModel(): EmployeesViewModel {
        return employeeTimingsViewModel
    }

    private fun openDateSelectFilterItem(employee: Employee) {
        MaterialDatePicker.Builder.datePicker()
            .showDatePickerDialog(activity = this.requireActivity(),
                defaultSelectedDate = System.currentTimeMillis(),
                onDateSelected = {
                    vm.onNewDateSelected(employee.id, it)
                })
    }

    private fun onEmployeeClicked(employeeIdAndDatePair: Pair<Int, Long>) {

//        lifecycleScope.launch {
//            vm.dataManager.addEmployeeTimingsList(EmployeeDetailsData.getEmployeesTimingsList())
//        }
        val directions = EmployeesFragmentDirections.navigateToEmployeeTimingsScreen(
            employeeIdAndDatePair.first, employeeIdAndDatePair.second
        )
        findNavController().navigate(directions)
    }
}