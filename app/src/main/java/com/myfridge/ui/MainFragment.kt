package com.myfridge.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.myfridge.R
import com.myfridge.auth.FirebaseInstance.auth
import com.myfridge.ui.main.MainActivity
import com.myfridge.ui.registration.RegistrationActivity
import com.myfridge.util.UserSettings
import com.myfridge.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mainActivity: MainActivity
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity

        mainActivityViewModel.account.observe(viewLifecycleOwner, { account ->
            //account.photoUrl = "qwe"
            mainTextView.setText(account.toString())
            //TODO add photo to imageView
        })

        profileButton.setOnClickListener {

        }
    }
}