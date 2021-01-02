package com.example.tabatatimer.activities

import android.R.color
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.tabatatimer.Constants.LOCALE_PREFERENCE
import com.example.tabatatimer.R
import com.example.tabatatimer.themes.ActivityTheme
import java.util.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val lang = preferences.getString(LOCALE_PREFERENCE, "en")
        val config = Configuration()
        val locale = Locale(lang!!)
        Locale.setDefault(locale)
        config.setLocale(locale)
        this.baseContext.resources.updateConfiguration(config, this.baseContext.resources.displayMetrics)

        ActivityTheme.setActivityTheme(this)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}