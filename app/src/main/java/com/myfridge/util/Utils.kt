package com.myfridge.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.myfridge.auth.PhoneAuth
import com.myfridge.ui.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_registration.*

object Utils {
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

     fun showProgressBar(activity: RegistrationActivity, text: String = "") {
        activity.progress_circular.visibility = View.VISIBLE
        activity.progress_textView.visibility = View.VISIBLE
        if (text != "") {
            activity.progress_textView.text = text
        }

        activity.reg_nav_host_fragment.visibility = View.INVISIBLE
    }
     fun hideProgressBar(activity: RegistrationActivity) {
        activity.progress_circular.visibility = View.GONE
        activity.progress_textView.visibility = View.GONE
        activity.reg_nav_host_fragment.visibility = View.VISIBLE
    }
}