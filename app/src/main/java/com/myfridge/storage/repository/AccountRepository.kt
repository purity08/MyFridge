package com.myfridge.storage.repository

import androidx.lifecycle.LiveData
import com.myfridge.storage.entity.Account

interface AccountRepository {

    fun get(): LiveData<Account>

    suspend fun insert(account: Account)

    suspend fun delete(account: Account)

    suspend fun update(account: Account)
}