package com.acquaexchange.base

import androidx.lifecycle.ViewModel
import com.acquaexchange.base.utils.ConflatedChannel
import com.acquaexchange.base.utils.sendValue

abstract class BaseViewModel(
    internal val dataManager: BaseDataManager,
) : ViewModel() {

    val displayToastChannel = ConflatedChannel<String>()

    suspend fun clearAllStoredData() {
        dataManager.clearDatabase()
    }

    fun displayToast(toastMessage: String){
        displayToastChannel.sendValue(toastMessage)
    }
}