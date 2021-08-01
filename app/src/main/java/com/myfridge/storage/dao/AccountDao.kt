package com.myfridge.storage.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.myfridge.storage.entity.Account

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun get(): LiveData<Account>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: Account)

    @Update
    fun update(account: Account)

    @Delete
    fun delete(account: Account)

}