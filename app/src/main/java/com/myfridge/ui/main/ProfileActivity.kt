package com.myfridge.ui.main

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myfridge.R
import com.myfridge.storage.entity.Account
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File

class ProfileActivity: AppCompatActivity(R.layout.activity_profile) {

    private lateinit var profile: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profile = intent.extras!!.get("profile") as Account

        initializeUI()
    }
    private fun initializeUI() {
        profileImage.setImageURI(Uri.fromFile(File(profile.imagePath)))
        profileName.text = profile.name
        profileLastName.text = profile.lastName
        profileEmail.text = profile.email
        profilePhoneNumber.text = profile.phoneNumber
    }
}