package com.myfridge

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myfridge.ui.main.MainActivity
import com.myfridge.ui.registration.RegistrationActivity
import com.myfridge.util.UserSettings

class StartActivity : AppCompatActivity(R.layout.activity_start) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity: AppCompatActivity =
            if ((!UserSettings.getFirstLogin(this)) ||
                (!UserSettings.getFirstGoogleLogin(this))
            ) {
                MainActivity()
            } else {
                RegistrationActivity()
            }

        navigateTo(activity)
    }

    private fun navigateTo(activity: AppCompatActivity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
        finish()
    }
}