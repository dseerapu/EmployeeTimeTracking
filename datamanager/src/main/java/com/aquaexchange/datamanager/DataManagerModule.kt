package com.aquaexchange.datamanager

import android.content.Context
import androidx.room.Room
import com.acquaexchange.base.BaseDataManager
import com.aquaexchange.datamanager.data_manager.DataManager
import com.aquaexchange.datamanager.data_manager.DataManagerImpl
import com.aquaexchange.datamanager.db.AppDatabase
import com.aquaexchange.datamanager.db.helper.DBHelper
import com.aquaexchange.datamanager.db.helper.DBHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataManagerModule {

    /**
     * Provides Data Manager instance
     */
    @Provides
    @Singleton
    internal fun providesDataManager(dataManager: DataManagerImpl): DataManager {
        return dataManager
    }

    @Provides
    @Singleton
    internal fun providesBaseDataManager(
        dataManager: DataManager
    ): BaseDataManager {
        return dataManager
    }


    /**
     * Provides DB Helper instance
     */
    @Provides
    @Singleton
    internal fun providesDBHelper(dbHelper: DBHelperImpl): DBHelper {
        return dbHelper
    }

    @Provides
    internal fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "EmployeeDB")
            .fallbackToDestructiveMigrationOnDowngrade()
            .fallbackToDestructiveMigration()
            .build()
    }

}