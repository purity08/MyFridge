package com.myfridge

import android.app.Application
import androidx.room.Room
import com.myfridge.storage.AppDatabase
import timber.log.Timber
import timber.log.Timber.DebugTree

class MyFridgeApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, AppDatabase.NAME_DB).build()
    }
    companion object {
        lateinit var database: AppDatabase
            private set
    }
}