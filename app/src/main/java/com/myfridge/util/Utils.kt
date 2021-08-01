package com.myfridge.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.myfridge.ui.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_registration.*
import java.net.URL
import android.app.DownloadManager
import android.net.Uri
import android.widget.Toast

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.myfridge.auth.FirebaseInstance.auth
import com.myfridge.storage.entity.Account
import com.myfridge.viewModel.MainActivityViewModel
import timber.log.Timber
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection


object Utils {

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
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

    fun getBitmapFromURL(src: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val url = URL(src)
            val connection: HttpURLConnection = url
                .openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            bitmap = BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun saveToGallery(context: Context, bitmap: Bitmap, name: String): String {
        val filename = "${name}.jpg"
        val write: (OutputStream) -> Unit = {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, it)
            it.flush()
            it.close()
        }
        val imagesDir =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + File.separator
        val filePath = File(imagesDir)
        filePath.mkdirs()
        val image = File(imagesDir, filename)
        write(FileOutputStream(image))
        return image.absolutePath
    }


}