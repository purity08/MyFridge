package com.myfridge.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.myfridge.storage.entity.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAll(): LiveData<List<Product>>

    @Query("SELECT * FROM product WHERE name=:productName")
    fun getByName(productName: String): LiveData<Product>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)
}