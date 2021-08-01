package com.myfridge.auth

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.myfridge.auth.FirebaseInstance.auth
import com.myfridge.ui.registration.RegistrationActivity
import com.myfridge.util.UserSettings
import com.myfridge.util.Utils.hideProgressBar
import com.myfridge.util.Utils.showProgressBar
import kotlinx.android.synthetic.main.activity_registration.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


object PhoneAuth {

    private lateinit var activity: AppCompatActivity

    private var verificationId: String by Delegates.notNull()
    private var resendToken: PhoneAuthProvider.ForceResendingToken by Delegates.notNull()
    private var credential: PhoneAuthCredential by Delegates.notNull()

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(recievedCredential: PhoneAuthCredential) {
            Timber.d("onVerificationCompleted:$recievedCredential")
            credential = recievedCredential
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Timber.w("onVerificationFailed $e")

            hideProgressBar(activity as RegistrationActivity)
            Toast.makeText(activity, e.localizedMessage, Toast.LENGTH_LONG).show()
            /*
            if (e is FirebaseAuthInvalidCredentialsException) {

            } else if (e is FirebaseTooManyRequestsException) {

            }

             */
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {

            Timber.d("onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            this@PhoneAuth.verificationId = verificationId
            resendToken = token

            //Navigate to the step 2
            (activity as RegistrationActivity).regFirstStepFragment!!.navigateToSecondStep()

            //hide progress bar, show the next fragment
            hideProgressBar(activity as RegistrationActivity)
        }
    }

    fun startPhoneVerification(activity: AppCompatActivity, phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        this.activity = activity

        //show circular progress bar while auth
        showProgressBar(activity as RegistrationActivity)

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyPhoneNumberWithCode(code: String) {
        showProgressBar(activity as RegistrationActivity, text = "Verifying..")

        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithCredential:success")

                    //save first Login
                    UserSettings.saveFirstLogin(activity, value = true)

                    //navigate to step 3
                    (activity as RegistrationActivity).regSecondStepFragment!!.navigateToThirdStep()
                    hideProgressBar(activity as RegistrationActivity)

                } else {
                    // Sign in failed, display a message and update the UI
                    Timber.w("signInWithCredential:failure ${task.exception}")

                    hideProgressBar(activity as RegistrationActivity)

                    Toast.makeText(activity, "wrong code", Toast.LENGTH_LONG).show()
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid
//                    }
                    // Update UI
                }
            }
    }

}