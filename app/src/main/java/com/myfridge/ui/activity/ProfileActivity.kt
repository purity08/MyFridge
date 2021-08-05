package com.myfridge.ui.activity

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.myfridge.R
import com.myfridge.storage.entity.Account
import com.myfridge.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File

class ProfileActivity: AppCompatActivity(R.layout.activity_profile) {

    private lateinit var profile: Account
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profile = intent.extras!!.get("profile") as Account

        initializeUI()

        saveButton.setOnClickListener {
            if ((!profileName.equals(profile.name))
                || (!profileLastName.equals(profile.lastName))
                || (!profileEmail.equals(profile.email))
                || (!profilePhoneNumber.equals(profile.phoneNumber))
            ){
                val account = Account(
                    id = profile.id,
                    name = profileName.text.toString(),
                    lastName = profileLastName.text.toString(),
                    email = profileEmail.text.toString(),
                    phoneNumber = profilePhoneNumber.text.toString(),
                    imagePath = profile.imagePath
                )
                mainActivityViewModel.updateAccount(account)
            }
            finish()
        }
        backButton.setOnClickListener {
            finish()
        }
    }
    private fun initializeUI() {
        profileImage.setImageURI(Uri.fromFile(File(profile.imagePath)))
        profileName.setText(profile.name)
        profileLastName.setText(profile.lastName)
        profileEmail.setText(profile.email)
        profilePhoneNumber.setText(profile.phoneNumber)
    }
}