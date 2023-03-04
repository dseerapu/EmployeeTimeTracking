package com.example.aquaexchange.ui.create_employee

import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.acquaexchange.base.BaseBottomSheetDialogFragment
import com.example.aquaexchange.R
import com.example.aquaexchange.databinding.BottomSheetFragmentCreateDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateEmployeeBottomSheetDialogFragment :
    BaseBottomSheetDialogFragment<CreateEmployeeViewModel, BottomSheetFragmentCreateDialogBinding>() {

    private val createEmployeeViewModel: CreateEmployeeViewModel by viewModels()

    override fun initObservers(viewLifecycleOwner: LifecycleOwner) {
        lifecycleScope.launchWhenResumed {
            vm.closeCreateEmployeeDialog.receive()
            dismissAllowingStateLoss()
        }
    }

    override fun setUp() {
        dataBinding.viewModel = vm
    }

    override fun getViewModel(): CreateEmployeeViewModel {
        return createEmployeeViewModel
    }

    override val TAG = "CreateEmployeeScreen"

    override fun getLayoutResource() = R.layout.bottom_sheet_fragment_create_dialog
}