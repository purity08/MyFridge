package com.myfridge.storage

data class Account(
    val name: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String = "",
    val photoUrl: String
)
