package com.myfridge.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.myfridge.ui.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_registration.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


object Utils {

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun View.setVisibility(isVisible: Boolean) {
        this.visibility = if(!isVisible) View.INVISIBLE else View.VISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

     fun distance(activity: AppCompatActivity, x1: Int, y1: Int, x2: Float, y2: Float): Float {
        val dx = x1 - x2
        val dy = y1 - y2
        val distanceInPx = Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
        return pxToDp(activity, distanceInPx)
    }

     private fun pxToDp(activity: AppCompatActivity, px: Float): Float {
        return px / activity.resources.displayMetrics.density
    }

    fun showProgressBar(activity: RegistrationActivity, text: String = "") {
        activity.progress_circular.setVisibility(true)
        activity.progress_textView.setVisibility(true)
        if (text != "") {
            activity.progress_textView.text = text
        }

        activity.reg_nav_host_fragment.setVisibility(false)
    }

    fun hideProgressBar(activity: RegistrationActivity) {
        activity.progress_circular.gone()
        activity.progress_textView.gone()
        activity.reg_nav_host_fragment.setVisibility(true)
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