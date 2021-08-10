package com.myfridge.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myfridge.storage.dao.AccountDao
import com.myfridge.storage.dao.ProductDao
import com.myfridge.storage.entity.Account
import com.myfridge.storage.entity.Category
import com.myfridge.storage.entity.Product

@Database(entities = [Account::class, Product::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun accountDao(): AccountDao

    abstract fun productDao(): ProductDao

    companion object {
        const val NAME_DB = "MY_FRIDGE_DB"
    }
}