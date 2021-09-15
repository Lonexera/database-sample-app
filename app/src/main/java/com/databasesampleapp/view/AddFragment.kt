package com.databasesampleapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.databasesampleapp.R
import com.databasesampleapp.databinding.FragmentAddBinding
import com.databasesampleapp.db.room.Dog

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    interface AddFragmentListener {
        fun insertDog(dog: Dog)
        fun updateDog(dog: Dog)
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

        val listener = activity as AddFragmentListener

        with (binding) {
            nameEdit.setOnKeyListener { _, _, _ ->
                if (isInputNameValid()) {
                    // Clear the error.
                    nameEdit.error = null
                }
                false
            }
            ageEdit.setOnKeyListener { _, _, _, ->
                if(isInputAgeValid()) {
                    ageEdit.error = null
                }
                false
            }
            breedEdit.setOnKeyListener { _, _, _ ->
                if(isInputBreedValid()) {
                    breedEdit.error = null
                }
                false
            }
        }

        binding.addButton.setOnClickListener {
            if(isInputValid()) {
                listener.insertDog(getAddedDog())
                navigateToList()
            }
            else showInputErrorTexts()
        }

        binding.addToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.action_save -> {
                    if(isInputValid()) {
                        listener.insertDog(getAddedDog())
                        navigateToList()
                    }
                    else showInputErrorTexts()
                    true
                }
                else -> true
            }
        }
        binding.addToolbar.setNavigationOnClickListener {
            navigateToList()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


    private fun getAddedDog(): Dog {
        return Dog(
            binding.nameEdit.text.toString(),
            binding.ageEdit.text.toString().toInt(),
            binding.breedEdit.text.toString()
        )
    }

    private fun isInputValid(): Boolean {
        return isInputNameValid() &&
                isInputAgeValid() &&
                isInputBreedValid()
    }

    private fun isInputNameValid(): Boolean {
        val nameEditText = binding.nameEdit.text.toString()
        return nameEditText.matches(""".+""".toRegex())
    }
    private fun isInputAgeValid(): Boolean {
        val ageEditText = binding.ageEdit.text.toString()
        return ageEditText.matches("""[0-9]{1,3}""".toRegex())
    }
    private fun isInputBreedValid(): Boolean {
        val breedEditText = binding.breedEdit.text.toString()
        return breedEditText.matches(""".+""".toRegex())
    }

    private fun showInputErrorTexts() {
        with(binding) {
            if (!isInputNameValid()) nameEdit.error = getString(R.string.name_edit_error)
            if (!isInputAgeValid()) ageEdit.error = getString(R.string.age_edit_error)
            if (!isInputBreedValid()) breedEdit.error = getString(R.string.breed_edit_error)
        }
        showInputErrorToast()
    }
    private fun showInputErrorToast() {
        Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT)
            .show()
    }

    private fun navigateToList() {
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }
}