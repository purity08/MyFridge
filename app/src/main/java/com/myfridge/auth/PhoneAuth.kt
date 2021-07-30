package com.myfridge.auth

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.myfridge.auth.FirebaseInstance.auth
import com.myfridge.ui.registration.RegistrationActivity
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


object PhoneAuth {
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

//            if (e is FirebaseAuthInvalidCredentialsException) {
//
//            } else if (e is FirebaseTooManyRequestsException) {
//
//            }

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
        }
    }

    fun startPhoneVerification(activity: AppCompatActivity, phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyPhoneNumberWithCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        //Bad code
            val activity = RegistrationActivity()
        //
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithCredential:success")

                    //UserSettings.saveFirstLogin(this, false)

                } else {
                    // Sign in failed, display a message and update the UI
                    Timber.w("signInWithCredential:failure ${task.exception}")
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid
//                    }
                    // Update UI
                }
            }
    }
}