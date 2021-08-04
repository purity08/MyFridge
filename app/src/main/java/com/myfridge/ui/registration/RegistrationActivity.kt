package com.myfridge.ui.registration

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.gson.Gson
import com.myfridge.R
import com.myfridge.ui.main.MainActivity
import timber.log.Timber
import com.myfridge.auth.FirebaseInstance.auth
import com.myfridge.storage.entity.Account
import com.myfridge.util.UserSettings
import com.myfridge.viewModel.AddAccountViewModel


class RegistrationActivity : AppCompatActivity(R.layout.activity_registration) {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    var phoneNumber: String = ""
    var regFirstStepFragment: RegFirstStepFragment? = null
    var regSecondStepFragment: RegSecondStepFragment? = null

    private val addAccountViewModel: AddAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth.useAppLanguage()

        buildGoogleSignIn()
    }

     fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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
                    UserSettings.saveFirstGoogleLogin(this, value = true)

                    val user = auth.currentUser
                    val account = Account(
                        user!!.uid,
                        user.displayName,
                        lastName = "",
                        user.email,
                        user.phoneNumber,
                        user.photoUrl.toString()
                    )
                    account.isGoogleAccount = true

                    addAccountViewModel.insert(account)

                    navigateToMainActivity()
                } else {
                    Timber.d("signInWithCredential:failure ${task.exception}")
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }

    companion object {
        const val RC_SIGN_IN = 1
    }
}