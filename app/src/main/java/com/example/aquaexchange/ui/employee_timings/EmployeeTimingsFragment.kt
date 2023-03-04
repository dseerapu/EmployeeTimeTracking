package com.example.aquaexchange.ui.employee_timings

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.acquaexchange.base.BaseFragment
import com.acquaexchange.base.logger.logMessage
import com.example.aquaexchange.R
import com.example.aquaexchange.databinding.FragmentEmployeeTimingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class EmployeeTimingsFragment :
    BaseFragment<EmployeeTimingsViewModel, FragmentEmployeeTimingsBinding>() {

    private val employeeTimingsViewModel: EmployeeTimingsViewModel by viewModels()

    private val adapter by lazy {
        EmployeeTimingsAdapter()
    }

    override fun initObservers(viewLifecycleOwner: LifecycleOwner) {
        lifecycleScope.launchWhenResumed {
            vm.employeeTimings.collectLatest {
                logMessage("It\t${it.size}")
                adapter.submitList(it)
                dataBinding.recyclerView.scrollToPosition(it.size - 1)
            }
        }
    }

    override fun setUp() {

        dataBinding.viewModel = vm
        dataBinding.recyclerView.adapter = adapter

        //Display recyclerview in reverse order to view latest check-in and check-out times
        (dataBinding.recyclerView.layoutManager as LinearLayoutManager).apply {
            reverseLayout = true
            stackFromEnd = true
        }
    }

    override fun getViewModel(): EmployeeTimingsViewModel {
        return employeeTimingsViewModel
    }

    override val TAG = "EmployeeTimingsScreen"

    override fun getLayoutResource() = R.layout.fragment_employee_timings
}