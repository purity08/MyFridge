package com.myfridge.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.myfridge.R
import com.myfridge.util.DownloadImage
import com.myfridge.util.UserSettings
import com.myfridge.util.Utils
import com.myfridge.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var touchListener: OnTouchListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeUI()
    }

    private fun initializeUI() {
        observeAccount()

        setupBottomNavigation()

        initializeImageTouch()
        profile_circle_image.setOnTouchListener(touchListener)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initializeImageTouch() {
        //motion for profile circle image
        var xDelta = 0
        var yDelta = 0

        val MAX_CLICK_DURATION = 1000
        val MAX_CLICK_DISTANCE = 60

        var pressStartTime = 0L
        var pressedX = 0
        var pressedY = 0
        var stayedWithinClickDistance = false

        touchListener = OnTouchListener { view, event ->
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()

            val lParams = view.layoutParams as FrameLayout.LayoutParams

            when (event.action and MotionEvent.ACTION_MASK) {

                MotionEvent.ACTION_DOWN -> {
                    xDelta = x - lParams.leftMargin
                    yDelta = y - lParams.topMargin

                    pressedX = x
                    pressedY = y
                    pressStartTime = System.currentTimeMillis()
                    stayedWithinClickDistance = true
                }

                MotionEvent.ACTION_UP -> {
                    val pressDuration = System.currentTimeMillis() - pressStartTime
                    if (pressDuration < MAX_CLICK_DURATION && stayedWithinClickDistance) {
                        navigateToProfile()
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    if (x - xDelta + view.width <= container.width && y - yDelta + view.height <= container.height && x - xDelta >= 0 && y - yDelta >= 0) {
                        val layoutParams = view.layoutParams as FrameLayout.LayoutParams
                        layoutParams.leftMargin = x - xDelta
                        layoutParams.topMargin = y - yDelta
                        layoutParams.rightMargin = 0
                        layoutParams.bottomMargin = 0
                        view.layoutParams = layoutParams
                    }
                    val distance = Utils.distance(
                        this,
                        pressedX,
                        pressedY,
                        event.x,
                        event.y
                    )
                    if (stayedWithinClickDistance && distance > MAX_CLICK_DISTANCE) {
                        stayedWithinClickDistance = false
                    }
                }
            }
            container.invalidate()
            true
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

                UserSettings.saveFirstStart(this, value = false)
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

