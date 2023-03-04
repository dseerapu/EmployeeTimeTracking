package com.example.aquaexchange.ui.employees.adapter

import android.view.ViewGroup
import com.acquaexchange.base.BaseListAdapter
import com.acquaexchange.base.BaseViewHolder
import com.acquaexchange.base.utils.inflate
import com.aquaexchange.datamanager.db.employee.Employee
import com.example.aquaexchange.R
import com.example.aquaexchange.databinding.ItemEmployeeBinding

class EmployeesAdapter(
    val onEmployeeClicked: (Employee) -> Unit
) : BaseListAdapter<Employee>(Employee.DiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesHolder =
        EmployeesHolder(parent.inflate(R.layout.item_employee))

    inner class EmployeesHolder(binding: ItemEmployeeBinding) :
        BaseViewHolder<ItemEmployeeBinding, Employee>(binding) {

        init {
            binding.root.setOnClickListener {
                onEmployeeClicked.invoke(getItem(layoutPosition))
            }
        }

        override fun onBind(item: Employee) {
            binding.item = item
        }

    }
}