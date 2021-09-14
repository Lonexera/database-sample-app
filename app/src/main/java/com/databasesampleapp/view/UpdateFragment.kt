package com.databasesampleapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.databasesampleapp.R
import com.databasesampleapp.databinding.FragmentAddBinding

class UpdateFragment : Fragment() {

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

        binding.addButton.text = getString(R.string.update_button_text)
        binding.addToolbar.title = getString(R.string.update_toolbar_title)



        binding.addButton.setOnClickListener {
            // TODO implement update button listener
        }

        binding.addToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.action_save -> {
                    // TODO implement save action listener(update)
                    true
                }
                else -> true
            }
        }
        binding.addToolbar.setNavigationOnClickListener {
            // TODO implement update toolbar navigation listener
        }

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}