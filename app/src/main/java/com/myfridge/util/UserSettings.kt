package com.myfridge.util

import android.content.Context
import android.content.SharedPreferences

object UserSettings {

    private const val SHARED_PREFS_FILE_NAME = "UserSettings"
    private const val keyRegUser = "keyRegUser"
    private const val keyFirstLogin = "keyFirstLogin"
    private const val keyFirstStart = "keyFirstStart"
    private const val keyFirstGoogleLogin = "keyFirstGoogleLogin"
    private const val keySaveTabsCount = "keySaveTabsCount"

    fun saveRegUser(context: Context, value: Boolean) {
        getPrefs(context).edit().putBoolean(keyRegUser, value).apply()
    }

    fun getRegUser(context: Context): Boolean {
        return getPrefs(context).getBoolean(keyRegUser, false)
    }

    fun saveFirstStart(context: Context, value: Boolean) {
        getPrefs(context).edit().putBoolean(keyFirstStart, value).apply()
    }

    fun getFirstStart(context: Context): Boolean {
        return getPrefs(context).getBoolean(keyFirstStart, true)
    }

    fun saveFirstLogin(context: Context, value: Boolean) {
        getPrefs(context).edit().clear().putBoolean(keyFirstLogin, value).apply()
    }

    fun getFirstLogin(context: Context): Boolean {
        return getPrefs(context).getBoolean(keyFirstLogin, false)
    }

    fun saveFirstGoogleLogin(context: Context, value: Boolean) {
        getPrefs(context).edit().clear().putBoolean(keyFirstGoogleLogin, value).apply()
    }

    fun getFirstGoogleLogin(context: Context): Boolean {
        return getPrefs(context).getBoolean(keyFirstGoogleLogin, false)
    }

    fun saveTabsCount(context: Context, value: Int) {
        //getPrefs(context).edit().clear().putInt(keySaveTabsCount, value).apply()
        getPrefs(context).edit().remove("keySaveTabsCount").putInt(keySaveTabsCount, value).apply()
    }

    fun getTabsCount(context: Context): Int {
        return getPrefs(context).getInt(keySaveTabsCount, 1)
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }
}