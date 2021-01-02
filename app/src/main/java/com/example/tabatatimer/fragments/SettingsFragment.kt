package com.example.tabatatimer.fragments

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.tabatatimer.Constants.CLEAR_DATA_PREFERENCE
import com.example.tabatatimer.Constants.LOCALE_PREFERENCE
import com.example.tabatatimer.Constants.NIGHT_MODE_PREFERENCE
import com.example.tabatatimer.Constants.RECREATE
import com.example.tabatatimer.R
import com.example.tabatatimer.PreferenceHelper
import java.util.*

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


        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == NIGHT_MODE_PREFERENCE) {
                PreferenceHelper.setActivityTheme(requireContext())
            }
            val intent = requireActivity().intent.putExtra(RECREATE, true)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            requireActivity().finish()
            requireActivity().overridePendingTransition(0, 0)
            startActivity(intent)
            PreferenceHelper.updateLocale(requireContext(), requireActivity())
        }
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onDetach() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        preferences.unregisterOnSharedPreferenceChangeListener(listener)

        super.onDetach()
    }

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}