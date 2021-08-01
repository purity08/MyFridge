package com.myfridge.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.myfridge.R
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
            mainTextView.setText(account.toString())
            //TODO add photo to imageView
        })

        logout_button.setOnClickListener {
            val intent = Intent(requireContext(), RegistrationActivity()::class.java)
            startActivity(intent)
            mainActivity.finish()

            UserSettings.saveFirstLogin(requireContext(), false)
            UserSettings.saveFirstGoogleLogin(requireContext(), false)
        }
    }
}