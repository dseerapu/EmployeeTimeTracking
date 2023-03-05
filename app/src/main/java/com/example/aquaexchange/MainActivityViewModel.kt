package com.example.aquaexchange

import com.acquaexchange.base.BaseViewModel
import com.aquaexchange.datamanager.data_manager.DataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(dataManager: DataManager) :
    BaseViewModel(dataManager)