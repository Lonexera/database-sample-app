package com.databasesampleapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.databasesampleapp.FragmentListener
import com.databasesampleapp.R
import com.databasesampleapp.viewModels.UpdateViewModel
import com.databasesampleapp.databinding.FragmentAddBinding
import com.databasesampleapp.viewModels.DogViewModelFactory

class UpdateFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpdateViewModel by viewModels {
        DogViewModelFactory((activity as FragmentListener).getRepository())
    }

    private var dogId = 0

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

        val safeArgs: UpdateFragmentArgs by navArgs()
        dogId = safeArgs.dogId

        with(binding) {
            addButton.text = getString(R.string.update_button_text)
            addToolbar.title = getString(R.string.update_toolbar_title)

            nameEdit.setText(safeArgs.dogName)
            ageEdit.setText(safeArgs.dogAge.toString())
            breedEdit.setText(safeArgs.dogBreed)

            addButton.setOnClickListener {
                onUpdate()
            }

            addToolbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_save -> {
                        onUpdate()
                        true
                    }
                    else -> true
                }
            }
            addToolbar.setNavigationOnClickListener {
                viewModel.onBack()
            }
        }

        with(viewModel) {
            toList.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    findNavController().navigate(R.id.action_updateFragment_to_listFragment)
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
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun onUpdate() {
        viewModel.onUpdateBtnClicked(
            binding.nameEdit.text.toString(),
            binding.ageEdit.text.toString(),
            binding.breedEdit.text.toString(),
            dogId
        )
    }
}
