package com.databasesampleapp.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.databasesampleapp.FragmentListener
import com.databasesampleapp.viewModels.ListViewModel
import com.databasesampleapp.R
import com.databasesampleapp.viewModels.DogViewModelFactory

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: ListViewModel by viewModels {
        DogViewModelFactory((activity as FragmentListener).getRepository())
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_screen, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onChangedPrefs(requireContext())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
