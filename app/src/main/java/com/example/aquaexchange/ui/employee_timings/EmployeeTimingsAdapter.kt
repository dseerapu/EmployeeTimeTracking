package com.example.aquaexchange.ui.employee_timings

import android.view.ViewGroup
import com.acquaexchange.base.BaseListAdapter
import com.acquaexchange.base.BaseViewHolder
import com.acquaexchange.base.utils.inflate
import com.aquaexchange.datamanager.db.employee_timings.EmployeeTimings
import com.example.aquaexchange.R
import com.example.aquaexchange.databinding.ItemEmployeeTimingsBinding

class EmployeeTimingsAdapter : BaseListAdapter<EmployeeTimings>(EmployeeTimings.DiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EmployeeTimingsHolder(parent.inflate(R.layout.item_employee_timings))

    inner class EmployeeTimingsHolder(binding: ItemEmployeeTimingsBinding) :
        BaseViewHolder<ItemEmployeeTimingsBinding, EmployeeTimings>(binding) {

        override fun onBind(item: EmployeeTimings) {
            binding.item = item
        }

    }
}