package com.myfridge.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.options
import com.myfridge.R
import com.myfridge.auth.FirebaseInstance
import com.myfridge.storage.Account
import com.myfridge.ui.main.MainActivity
import com.myfridge.ui.registration.RegistrationActivity
import com.myfridge.util.UserSettings
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mainActivity: MainActivity
    private lateinit var account: Account

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        account = mainActivity.account

        mainTextView.text = account.toString()

        logout_button.setOnClickListener {
            val intent = Intent(requireContext(), RegistrationActivity()::class.java)
            startActivity(intent)
            mainActivity.finish()
            UserSettings.saveFirstLogin(requireContext(), true)
            UserSettings.saveFirstGoogleLogin(requireContext(), true)
        }
    }
}