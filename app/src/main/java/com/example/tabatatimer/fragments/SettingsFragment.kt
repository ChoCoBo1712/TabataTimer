package com.example.tabatatimer.fragments

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.tabatatimer.R
import java.util.prefs.PreferenceChangeEvent

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findPreference<Preference>("com.example.tabatatimer.CLEAR_DATA_PREFERENCE")?.setOnPreferenceClickListener {
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
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}