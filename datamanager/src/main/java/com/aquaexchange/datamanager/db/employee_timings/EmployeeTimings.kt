package com.aquaexchange.datamanager.db.employee_timings

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aquaexchange.datamanager.db.employee_timings.EmployeeTimings.CheckType
import com.aquaexchange.datamanager.utils.displayTimeInString
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(
    tableName = "employeeTimings"
)
data class EmployeeTimings(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val employeeId: Int,
    val checkInTime: Long,
    val checkOutTime: Long,

    /***
     * Refer [CheckType] for possible values
     */
    val checkType: String, // [CheckType]

    val dateInString: String,

    val date: Date
) : Parcelable {

    class DiffUtils : DiffUtil.ItemCallback<EmployeeTimings>() {

        override fun areItemsTheSame(oldItem: EmployeeTimings, newItem: EmployeeTimings) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: EmployeeTimings, newItem: EmployeeTimings
        ) =
            oldItem.employeeId == newItem.employeeId && oldItem.checkInTime == newItem.checkInTime && oldItem.checkOutTime == newItem.checkOutTime
    }

    val checkInTimeInString: String
        get() = checkInTime.displayTimeInString()

    val checkOutTimeInString: String
        get() = checkOutTime.displayTimeInString()

    val visibleCheckInTxt: Boolean
        get() = checkInTime >= checkOutTime

    val visibleCheckOutTxt: Boolean
        get() = checkOutTime >= checkInTime

    enum class CheckType {
        CheckIn, CheckOut
    }
}