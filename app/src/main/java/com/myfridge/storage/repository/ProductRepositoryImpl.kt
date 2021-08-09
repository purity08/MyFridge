package com.myfridge.storage.repository

import androidx.lifecycle.LiveData
import com.myfridge.MyFridgeApp
import com.myfridge.storage.AppDatabase
import com.myfridge.storage.entity.Product

class ProductRepositoryImpl : ProductRepository {

    private val database: AppDatabase by lazy {
        MyFridgeApp.database
    }

    override fun getAll(): LiveData<List<Product>> = database.productDao().getAll()

    override fun getByName(productName: String): LiveData<Product> =
        database.productDao().getByName(productName)

    override suspend fun insert(product: Product) = database.productDao().insert(product)

    override suspend fun delete(product: Product) = database.productDao().delete(product)

    override suspend fun update(product: Product) = database.productDao().update(product)
}