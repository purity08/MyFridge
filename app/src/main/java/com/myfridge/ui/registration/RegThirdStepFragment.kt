package com.myfridge.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myfridge.R
import kotlinx.android.synthetic.main.fragment_reg_third_step.*


class RegThirdStepFragment : Fragment(R.layout.fragment_reg_third_step) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        completeButton.setOnClickListener {
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        (activity as RegistrationActivity).navigateToMainActivity()
    }
}