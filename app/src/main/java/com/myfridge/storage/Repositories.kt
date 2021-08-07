package com.myfridge.storage

import com.myfridge.storage.repository.AccountRepository
import com.myfridge.storage.repository.AccountRepositoryImpl

object Repositories {
    val accountRepository: AccountRepository by lazy {
        AccountRepositoryImpl()
    }

}