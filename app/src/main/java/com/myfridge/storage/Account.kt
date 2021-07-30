package com.myfridge.storage

import android.net.Uri

data class Account(
    val id: String,
    val name: String?,
    val lastName: String,
    val email: String?,
    val phoneNumber: String? = "",
    val photoUrl: Uri?
)
