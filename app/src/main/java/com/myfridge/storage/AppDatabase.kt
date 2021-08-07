package com.myfridge.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myfridge.storage.dao.AccountDao
import com.myfridge.storage.entity.Account
import com.myfridge.storage.entity.Category

@Database(entities = [Account::class], version = 3)
abstract class AppDatabase: RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        const val NAME_DB = "MY_FRIDGE_DB"
    }
}