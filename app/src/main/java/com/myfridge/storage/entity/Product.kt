package com.myfridge.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    val id: String,
    val name: String,
    var count: Int,
    val category: String,
)