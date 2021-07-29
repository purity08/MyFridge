package com.myfridge

import android.content.Context
import android.content.SharedPreferences

object UserSettings {

    private const val SHARED_PREFS_FILE_NAME = "UserSettings"
    private const val keyRegUser = "keyRegUser"
    private const val keyUserPhone = "keyUserPhone"
    private const val keyFirstLogin = "keyFirstLogin"

    fun saveRegUser(context: Context, value: Boolean) {
        getPrefs(context).edit().putBoolean(keyRegUser, value).apply()
    }

    fun getRegUser(context: Context): Boolean {
        return getPrefs(context).getBoolean(keyRegUser, false)
    }

    fun saveFirstLogin(context: Context, value: Boolean) {
        getPrefs(context).edit().putBoolean(keyFirstLogin, value).apply()
    }

    fun getFirstLogin(context: Context): Boolean {
        return getPrefs(context).getBoolean(keyFirstLogin, true)
    }

    fun saveUserPhone(context: Context, value: String) {
        getPrefs(context).edit().putString(keyUserPhone, value).apply()
    }

    fun getUserPhone(context: Context): String? {
        return getPrefs(context).getString(keyUserPhone,"")
    }


    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }
}