package com.databasesampleapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.databasesampleapp.R
import com.databasesampleapp.databinding.FragmentAddBinding
import com.databasesampleapp.db.room.Dog

class UpdateFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding = _binding!!

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

        val listener = activity as AddFragment.AddFragmentListener

        binding.addButton.text = getString(R.string.update_button_text)
        binding.addToolbar.title = getString(R.string.update_toolbar_title)



        binding.addButton.setOnClickListener {
            if(isInputValid())
                listener.updateDog(getUpdatedDog())
            else showInputErrorTexts()
        }

        binding.addToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.action_save -> {
                    if(isInputValid())
                        listener.updateDog(getUpdatedDog())
                    else showInputErrorTexts()
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


    private fun getUpdatedDog(): Dog {
        return Dog(
            binding.nameEdit.text.toString(),
            binding.ageEdit.text.toString().toInt(),
            binding.breedEdit.text.toString()
        ).apply { uid = dogId }
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
}