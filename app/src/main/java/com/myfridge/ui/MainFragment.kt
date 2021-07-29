package com.myfridge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.myfridge.R
import com.myfridge.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mainActivity: MainActivity
    private lateinit var account: FirebaseUser

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity

        account = FirebaseAuth.getInstance().currentUser!!
        //account = GoogleSignIn.getLastSignedInAccount(requireContext())

        mainTextView.text = "${account.displayName} \n ${account!!.email} \n"
    }
}