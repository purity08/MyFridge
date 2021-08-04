package com.myfridge.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Account(
    @PrimaryKey
    val id: String,
    var name: String? = "",
    var lastName: String = "",
    val email: String? = "",
    val phoneNumber: String? = "",
    var imagePath: String = ""
): Serializable {
    var isGoogleAccount = false
}
