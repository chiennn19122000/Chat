package com.example.chatgptfree.utils

import android.content.Context

class PreferenceHelper (private val context: Context, name: String = PREFERENCES_NAME) {
    companion object {
        val PREFERENCES_NAME = preferenceNameApp
    }

    private val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    var statusLogin: Boolean
        get() {
            return sharedPreferences.getBoolean(STATUS_LOGIN, false)
        }
        set(value) {
            sharedPreferences.edit().putBoolean(STATUS_LOGIN, value).apply()
        }

    var token: String
        get() {
            return sharedPreferences.getString(TOKEN,"")?:""
        }
        set(value) {
            sharedPreferences.edit().putString(TOKEN,"$BEARER $value").apply()
        }
}