package com.myfridge.storage

import com.myfridge.storage.repository.AccountRepository
import com.myfridge.storage.repository.AccountRepositoryImpl
import com.myfridge.storage.repository.ProductRepository
import com.myfridge.storage.repository.ProductRepositoryImpl

object Repositories {
    val accountRepository: AccountRepository by lazy {
        AccountRepositoryImpl()
    }

    val productsRepository: ProductRepository by lazy {
        ProductRepositoryImpl()
    }
}