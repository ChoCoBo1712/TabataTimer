package com.example.tabatatimer.fragments

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.app.ActivityCompat.recreate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.tabatatimer.Constants
import com.example.tabatatimer.Constants.CLEAR_DATA_PREFERENCE
import com.example.tabatatimer.Constants.LOCALE_PREFERENCE
import com.example.tabatatimer.Constants.RECREATE
import com.example.tabatatimer.R
import com.example.tabatatimer.activities.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import java.util.prefs.PreferenceChangeEvent
import kotlin.concurrent.thread

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findPreference<Preference>(CLEAR_DATA_PREFERENCE)?.setOnPreferenceClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.clear_data_dialog_message))
                .setPositiveButton(getString(R.string.clear)) { _, _ ->
                    (requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                        .clearApplicationUserData()
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                .setCancelable(true)
                .create()
                .show()

            true
        }

        updateLocale()

        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        listener = SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
            val intent = requireActivity().intent.putExtra(RECREATE, true)
            requireActivity().overridePendingTransition(0, 0)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            requireActivity().finish()
            requireActivity().overridePendingTransition(0, 0)
            startActivity(intent)
            updateLocale()
        }
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    private fun updateLocale() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val lang = preferences.getString(LOCALE_PREFERENCE, "en")
        val config = Configuration()
        val locale = Locale(lang!!)
        Locale.setDefault(locale)
        config.setLocale(locale)
        requireActivity().baseContext.resources.updateConfiguration(config, requireActivity().baseContext.resources.displayMetrics)
    }

    override fun onDetach() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
        updateLocale()

        super.onDetach()
    }

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}