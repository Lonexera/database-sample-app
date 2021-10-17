package com.databasesampleapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.databasesampleapp.R
import com.databasesampleapp.viewModels.UpdateViewModel
import com.databasesampleapp.databinding.FragmentAddBinding
import com.databasesampleapp.viewModels.DogViewModelFactory

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpdateViewModel by viewModels {
        DogViewModelFactory((activity as FragmentListener).getRepository())
    }

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

        with(viewModel) {
            toList.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    println("navigate to list")
                    findNavController().navigate(R.id.action_addFragment_to_listFragment)
                }
            }

            inputNameError.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    binding.nameEdit.error = getString(R.string.name_edit_error)
                }
            }

            inputAgeError.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    binding.ageEdit.error = getString(R.string.age_edit_error)
                }
            }

            inputBreedError.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    binding.breedEdit.error = getString(R.string.breed_edit_error)
                }
            }

            inputError.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        with(binding) {
            addButton.setOnClickListener {
                onAddButton()
            }

            addToolbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_save -> {
                        onAddButton()
                        true
                    }
                    else -> true
                }
            }
            addToolbar.setNavigationOnClickListener {
                viewModel.onBack()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun onAddButton() {
        viewModel.onAddBtnClicked(
            binding.nameEdit.text.toString(),
            binding.ageEdit.text.toString(),
            binding.breedEdit.text.toString()
        )
    }
}
