package com.aquaexchange.datamanager.db.employee

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "employee"
)
data class Employee(
    @PrimaryKey
    val id: Int,
    val name: String
) : Parcelable {

    class DiffUtils : DiffUtil.ItemCallback<Employee>() {

        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.name.equals(newItem.name, true)
        }

    }
}