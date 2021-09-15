package com.databasesampleapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.databasesampleapp.adapter.DogAdapter
import com.databasesampleapp.databinding.ActivityMainBinding
import com.databasesampleapp.db.room.Dog
import com.databasesampleapp.view.AddFragment
import com.databasesampleapp.view.ListFragment
import com.databasesampleapp.view.SettingsFragment
import com.databasesampleapp.viewModels.DogCursorViewModel
import com.databasesampleapp.viewModels.DogCursorViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CursorActivity: AppCompatActivity(), ListFragment.ListFragmentListener,
SettingsFragment.SettingsListener,
AddFragment.AddFragmentListener,
DogItemListener {

    private lateinit var binding: ActivityMainBinding
    private val cursorViewModel: DogCursorViewModel by viewModels {
        DogCursorViewModelFactory((application as DogsApplication).cursorRepository)
    }
    private lateinit var adapter: DogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = DogAdapter(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setListToAdapter(adapter: DogAdapter) {
        this.adapter = adapter
        onDataSetChanged()
    }

    private fun onDataSetChanged() {
        lifecycle.coroutineScope.launch {
            cursorViewModel.allDogs.collect() {
                adapter.submitList(filterListWithPrefs(it))
            }
        }
    }

    private fun openRoomActivity() {
        val intent = Intent(this, RoomActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onChangedPrefs() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        when(prefs.getString(getString(R.string.db_selector_key),
        getString(R.string.selector_room_entry))) {
            getString(R.string.selector_room_entry) -> openRoomActivity()
            else -> findNavController(binding.navHostFragmentContainer.id).popBackStack()
        }
    }

    override fun insertDog(dog: Dog) {
        cursorViewModel.insert(dog)
        onDataSetChanged()
    }

    override fun updateDog(dog: Dog) {
        cursorViewModel.update(dog)
        onDataSetChanged()
    }

    override fun deleteDog(dog: Dog) {
        cursorViewModel.delete(dog)
        onDataSetChanged()
    }

    override fun openUpdateScreen(dog: Dog) {
        TODO("Not yet implemented")
    }

    private fun filterListWithPrefs(list: List<Dog>): List<Dog> {
        var filteredList: List<Dog> = list

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val defaultValue = getString(R.string.filter_default_value)
        val prefName = prefs?.getString(
            getString(R.string.filter_name_key),
            defaultValue
        ) ?: defaultValue

        val prefAge = prefs?.getString(
            getString(R.string.filter_age_key),
            defaultValue
        ) ?: defaultValue

        val prefBreed = prefs?.getString(
            getString(R.string.filter_breed_key),
            defaultValue
        ) ?: defaultValue

        filteredList =
            if (prefName != defaultValue)
                filteredList.filter { it.name == prefName }
            else filteredList

        filteredList = if (prefAge != defaultValue &&
            prefAge.matches("""[0-9]{1,3}""".toRegex())) {
            filteredList.filter { it.age == prefAge.toInt() }
        }
        else filteredList

        filteredList = if (prefBreed != defaultValue)
            filteredList.filter { it.breed == prefBreed }
        else filteredList

        return filteredList
    }
}