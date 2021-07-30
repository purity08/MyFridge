package com.myfridge.util

import android.content.Context
import android.content.SharedPreferences

object UserSettings {

    private const val SHARED_PREFS_FILE_NAME = "UserSettings"
    private const val keyRegUser = "keyRegUser"
    private const val keyFirstLogin = "keyFirstLogin"
    private const val keyFirstGoogleLogin = "keyFirstGoogleLogin"

    fun saveRegUser(context: Context, value: Boolean) {
        getPrefs(context).edit().putBoolean(keyRegUser, value).apply()
    }

    fun getRegUser(context: Context): Boolean {
        return getPrefs(context).getBoolean(keyRegUser, false)
    }

    fun saveFirstLogin(context: Context, value: Boolean) {
        getPrefs(context).edit().clear().putBoolean(keyFirstLogin, value).apply()
    }


    fun getFirstLogin(context: Context): Boolean {
        return getPrefs(context).getBoolean(keyFirstLogin, true)
    }

    fun saveFirstGoogleLogin(context: Context, value: Boolean) {
        getPrefs(context).edit().clear().putBoolean(keyFirstGoogleLogin, value).apply()
    }

    fun getFirstGoogleLogin(context: Context): Boolean {
        return getPrefs(context).getBoolean(keyFirstGoogleLogin, true)
    }


    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }
}