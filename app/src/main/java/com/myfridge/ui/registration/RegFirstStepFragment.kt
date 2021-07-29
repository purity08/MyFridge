package com.myfridge.ui.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myfridge.R
import kotlinx.android.synthetic.main.fragment_reg_first_step.*


class RegFirstStepFragment : Fragment(R.layout.fragment_reg_first_step) {

    private lateinit var registrationActivity: RegistrationActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registrationActivity = activity as RegistrationActivity

        sendNumberButton.setOnClickListener {
            navigateToSecondStep()
        }

        googleSignButton.setOnClickListener {
            registrationActivity.signIn()
        }
    }

    private fun navigateToSecondStep() {
        findNavController().navigate(R.id.regSecondStepFragment)
    }


}