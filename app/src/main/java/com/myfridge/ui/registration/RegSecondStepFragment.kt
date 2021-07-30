package com.myfridge.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.myfridge.R
import com.myfridge.auth.PhoneAuth
import com.myfridge.util.Utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_reg_second_step.*

class RegSecondStepFragment : Fragment(R.layout.fragment_reg_second_step) {

    private lateinit var registrationActivity: RegistrationActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        registrationActivity = activity as RegistrationActivity
        registrationActivity.regSecondStepFragment = this

        confirmButton.setOnClickListener {

            val code = enterCodeEditText.text.toString()

            PhoneAuth.verifyPhoneNumberWithCode(code)
        }
        resentSmsCodeTextView.setOnClickListener {
            //PhoneAuth.resendVerificationCode()
        }
    }

    fun navigateToThirdStep() {
        findNavController().navigate(R.id.regThirdStepFragment)
    }

}