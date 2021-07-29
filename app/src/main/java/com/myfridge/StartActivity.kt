package com.myfridge

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myfridge.ui.registration.RegistrationActivity

class StartActivity : AppCompatActivity(R.layout.activity_start) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity: AppCompatActivity
        activity = RegistrationActivity()

        navigateTo(activity)
    }

    private fun navigateTo(activity: AppCompatActivity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
        finish()
    }
}