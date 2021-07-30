package com.myfridge.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.myfridge.R
import com.myfridge.storage.Account
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(R.layout.activity_main) {

    private lateinit var fbAccount: FirebaseUser
    lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fbAccount = FirebaseAuth.getInstance().currentUser!!
        account = Account(
            id = fbAccount.uid,
            name = fbAccount.displayName,
            lastName = "",
            email = fbAccount.email,
            phoneNumber = fbAccount.phoneNumber,
            photoUrl = fbAccount.photoUrl
        )

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottom_navigation.setupWithNavController(navController)
    }
}