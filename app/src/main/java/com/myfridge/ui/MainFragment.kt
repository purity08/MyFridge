package com.myfridge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.myfridge.R
import com.myfridge.storage.Account
import com.myfridge.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mainActivity: MainActivity
    private lateinit var account: Account

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as MainActivity
        account = mainActivity.account

        //account = GoogleSignIn.getLastSignedInAccount(requireContext())

        mainTextView.text = account.toString()
    }
}