package com.databasesampleapp.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.databasesampleapp.viewModels.ListViewModel
import com.databasesampleapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: ListViewModel by lazy {
        ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_screen, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.toList.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                findNavController().popBackStack()
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onChangedPrefs(requireContext())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
