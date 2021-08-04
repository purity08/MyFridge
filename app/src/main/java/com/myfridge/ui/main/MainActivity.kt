package com.myfridge.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.myfridge.R
import com.myfridge.util.DownloadImage
import com.myfridge.util.UserSettings
import com.myfridge.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeAccount()
        setupBottomNavigation()

        profile_circle_image.setOnClickListener {
            navigateToProfile()
        }
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottom_navigation.setupWithNavController(navController)
    }

    private fun observeAccount() {
        mainActivityViewModel.account.observe(this, { account ->
            if (UserSettings.getFirstGoogleLogin(this) && UserSettings.getFirstStart(this)
            ) {
                val path = DownloadImage(account.imagePath, this).execute("").get()
                account.imagePath = path.toString()
                account.lastName = account.name!!.split(" ")[1]
                account.name = account.name!!.split(" ")[0]
            }
           profile_circle_image.setImageURI(Uri.fromFile(File(account.imagePath)))
        })
    }

    private fun navigateToProfile() {
        val account = mainActivityViewModel.account.value

        val intent = Intent(this, ProfileActivity::class.java)
            .putExtra("profile", account)
        startActivity(intent)
    }
}

