package com.michael.statussaverstatusdownloader.utils


import androidx.appcompat.app.AppCompatDelegate

object ThemeUtils {
    fun applyTheme(theme: Int)  {
        when (theme) {
            Constants.SYSTEM_DEFAULT_THEME -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            Constants.LIGHT_THEME -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Constants.DARK_THEME -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    fun saveTheme(theme: Int) {
        SharedPreferenceUtils.putPrefInt(SharedPrefKeys.PREF_KEY_THEME, theme)
    }

    fun getTheme(): Int {
        return SharedPreferenceUtils.getPrefInt(SharedPrefKeys.PREF_KEY_THEME, Constants.SYSTEM_DEFAULT_THEME)
    }
}