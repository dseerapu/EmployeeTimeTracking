package com.aquaexchange.datamanager.data_manager

import com.aquaexchange.datamanager.db.helper.DBHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerImpl @Inject constructor(
    dbHelper: DBHelper
) : DataManager, DBHelper by dbHelper