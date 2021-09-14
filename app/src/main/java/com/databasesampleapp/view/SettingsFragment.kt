package com.databasesampleapp.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.databasesampleapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_screen, rootKey)
    }
}