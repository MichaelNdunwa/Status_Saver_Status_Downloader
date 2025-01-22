package com.michael.statussaverstatusdownloader.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceUtils {
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun init(context: Context) {
        preferences = context.getSharedPreferences(
            "${"Status Saver . Status Downloader".replace(" ", "_")}_prefs",
            Context.MODE_PRIVATE
        )
    }

    fun getPrefString(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }

    fun getPrefBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun getPrefInt(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun putPrefString(key: String, value: String): Boolean {
        editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
        return true
    }

    fun putPrefBoolean(key: String, value: Boolean): Boolean {
        editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
        return true
    }

    fun putPrefInt(key: String, value: Int): Boolean {
        editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
        return true
    }
}

object SharedPrefKeys {
    const val PREF_KEY_THEME = "pref_key_theme"
    const val PREF_KEY_STATUS_PERMISSION_GRANTED = "pref_key_status_permission_granted"
}