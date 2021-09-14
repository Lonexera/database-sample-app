package com.databasesampleapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.databasesampleapp.R
import com.databasesampleapp.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            // TODO implement add button listener
        }

        binding.addToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.action_save -> {
                    // TODO implement save action listener
                    true
                }
                else -> true
            }
        }
        binding.addToolbar.setNavigationOnClickListener {
            // TODO implement add toolbar navigation listener
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}