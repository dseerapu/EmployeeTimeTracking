package com.aquaexchange.datamanager.db.employee_day_stats

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aquaexchange.datamanager.utils.convertToHoursAndMinutes
import com.aquaexchange.datamanager.utils.displayTimeInString
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "employeeDayStats")
data class EmployeeDayStats(

    var employeeId: Int,
    var lastCheckIn: Long?,
    var lastCheckOut: Long?,
    var productiveTime: Long,

    var dateInString: String,

    var date: Date,

    @PrimaryKey(autoGenerate = true)
    val id: Int=0,

    ) : Parcelable {
    val productiveTimeInString: String
        get() = productiveTime.convertToHoursAndMinutes()

    val lastCheckInTimeInString: String
        get() = lastCheckIn.displayTimeInString()

    val lastCheckOutTimeInString: String
        get() = lastCheckOut.displayTimeInString()
}