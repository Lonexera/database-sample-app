package com.databasesampleapp.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.databasesampleapp.R

class FilterFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.filter_screen, rootKey)
    }
}
