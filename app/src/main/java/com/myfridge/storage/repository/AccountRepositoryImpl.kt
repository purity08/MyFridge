package com.myfridge.storage.repository

import androidx.lifecycle.LiveData
import com.myfridge.MyFridgeApp
import com.myfridge.storage.AppDatabase
import com.myfridge.storage.entity.Account
import kotlinx.coroutines.coroutineScope

class AccountRepositoryImpl: AccountRepository {

    private val database: AppDatabase by lazy {
        MyFridgeApp.database
    }

    override fun get(): LiveData<Account> = database.accountDao().get()

    override suspend fun insert(account: Account) = coroutineScope {
        database.accountDao().insert(account)
    }

    override suspend fun delete(account: Account) = coroutineScope {
        database.accountDao().delete(account)
    }

    override suspend fun update(account: Account) = coroutineScope {
        database.accountDao().update(account)
    }

}