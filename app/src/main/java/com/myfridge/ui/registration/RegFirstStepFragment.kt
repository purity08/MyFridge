package com.myfridge.ui.registration

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myfridge.R
import com.myfridge.auth.PhoneAuth
import kotlinx.android.synthetic.main.fragment_reg_first_step.*
import timber.log.Timber


class RegFirstStepFragment : Fragment(R.layout.fragment_reg_first_step) {

    private lateinit var registrationActivity: RegistrationActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

        registrationActivity = activity as RegistrationActivity

        sendNumberButton.setOnClickListener {
            val phoneNumber = countryCodePicker.selectedCountryCodeWithPlus +
                    phoneNumberEditText.text.toString().trim()

            Timber.d("phoneNumber: $phoneNumber")


            PhoneAuth.startPhoneVerification(registrationActivity, phoneNumber)
            navigateToSecondStep()
        }

        googleSignButton.setOnClickListener {
            registrationActivity.signInWithGoogle()
        }
    }

     private fun navigateToSecondStep() {
        findNavController().navigate(R.id.regSecondStepFragment)
    }
}