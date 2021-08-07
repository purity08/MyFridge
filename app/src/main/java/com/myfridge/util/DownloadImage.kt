package com.myfridge.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import com.myfridge.auth.FirebaseInstance.auth


class DownloadImage(
    private val src: String,
    @SuppressLint("StaticFieldLeak") val context: Context
) : AsyncTask<Any?, Any?, Any?>() {

    override fun doInBackground(vararg p0: Any?): String {

        val bitmap = Utils.getBitmapFromURL(src)!!

        return Utils.saveToGallery(
            context,
            bitmap,
            "user_${auth.currentUser!!.uid}"
        )
    }
}