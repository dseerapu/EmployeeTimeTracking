package com.acquaexchange.base.utils

import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.MaterialDatePicker
import timber.log.Timber

fun MaterialDatePicker.Builder<Long>.showDatePickerDialog(
    activity: FragmentActivity,
    defaultSelectedDate: Long?,
    onDateSelected: ((Long) -> Unit),
    onDatePickerClosed: (() -> Unit)? = null,
    minStartDate: Long = 0,
    maxEndDate: Long = Long.MAX_VALUE
) {

    setSelection(defaultSelectedDate)

    val picker = build()
    picker.show(activity.supportFragmentManager, picker.toString())

    picker.addOnNegativeButtonClickListener {
        onDatePickerClosed?.invoke()
        picker.dismiss()
    }

    picker.addOnCancelListener {
        onDatePickerClosed?.invoke()
        picker.dismiss()
    }

    picker.addOnPositiveButtonClickListener {
        onDateSelected.invoke(it)
        Timber.i("The selected date is $it")
    }
}
