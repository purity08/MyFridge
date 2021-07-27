package com.myfridge

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myfridge.ui.main.MainActivity

class StartActivity : AppCompatActivity(R.layout.activity_start) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateTo(MainActivity())
    }

    private fun navigateTo(activity: AppCompatActivity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
        finish()
    }
}