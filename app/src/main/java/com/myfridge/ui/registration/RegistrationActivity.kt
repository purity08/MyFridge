package com.myfridge.ui.registration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.myfridge.R
import com.myfridge.ui.main.MainActivity
import timber.log.Timber
import com.myfridge.auth.FirebaseInstance.auth
import com.myfridge.util.UserSettings


class RegistrationActivity : AppCompatActivity(R.layout.activity_registration) {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    var regFirstStepFragment: RegFirstStepFragment? = null
    var regSecondStepFragment: RegSecondStepFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth.useAppLanguage()

        buildGoogleSignIn()
    }

    fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    /**
     *
     * Sign In with Google
     *
     */

    fun signInWithGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun buildGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseGoogleAuth(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Timber.d("Google sign in failed $e")
            }
        }
    }

    private fun firebaseGoogleAuth(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    UserSettings.saveFirstGoogleLogin(this, false)
                    navigateToMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.d("signInWithCredential:failure ${task.exception}")
                }
            }
    }

    companion object {
        const val RC_SIGN_IN = 1
    }
}