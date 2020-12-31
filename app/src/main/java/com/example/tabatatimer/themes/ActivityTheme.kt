package com.example.tabatatimer.themes

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.tabatatimer.Constants.FONT_SIZE_PREFERENCE
import com.example.tabatatimer.Constants.NIGHT_MODE_PREFERENCE
import com.example.tabatatimer.R

class ActivityTheme {
    companion object {
        fun setActivityTheme(context: Context) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val nightModeEnabled = preferences.getBoolean(NIGHT_MODE_PREFERENCE, false)
            val fontSize = preferences.getString(FONT_SIZE_PREFERENCE, "1")

            if (nightModeEnabled) {
//                when (fontSize) {
//                    "0" -> context.setTheme(R.style.DarkTheme_SmallFont)
//                    "1" -> context.setTheme(R.style.DarkTheme_MediumFont)
//                    else -> context.setTheme(R.style.DarkTheme_LargeFont)
//                }
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else {
//                when (fontSize) {
//                    "0" -> context.setTheme(R.style.LightTheme_SmallFont)
//                    "1" -> context.setTheme(R.style.LightTheme_MediumFont)
//                    else -> context.setTheme(R.style.LightTheme_LargeFont)
//                }
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}