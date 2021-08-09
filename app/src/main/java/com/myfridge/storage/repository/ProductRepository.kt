package com.myfridge.storage.repository

import androidx.lifecycle.LiveData
import com.myfridge.storage.entity.Product

interface ProductRepository {

    fun getAll(): LiveData<List<Product>>

    fun getByName(productName: String): LiveData<Product>

    suspend fun insert(product: Product)

    suspend fun delete(product: Product)

    suspend fun update(product: Product)
}