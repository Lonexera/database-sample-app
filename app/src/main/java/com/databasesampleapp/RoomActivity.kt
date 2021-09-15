package com.databasesampleapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.databasesampleapp.adapter.DogAdapter
import com.databasesampleapp.databinding.ActivityMainBinding
import com.databasesampleapp.db.room.Dog
import com.databasesampleapp.view.AddFragment
import com.databasesampleapp.view.ListFragment
import com.databasesampleapp.view.SettingsFragment
import com.databasesampleapp.viewModels.DogRoomViewModel
import com.databasesampleapp.viewModels.DogRoomViewModelFactory

class RoomActivity : AppCompatActivity(), ListFragment.ListFragmentListener,
SettingsFragment.SettingsListener,
AddFragment.AddFragmentListener {

    private lateinit var binding: ActivityMainBinding
    private val roomViewModel: DogRoomViewModel by viewModels {
        DogRoomViewModelFactory((application as DogsApplication).roomRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setListToAdapter(adapter: DogAdapter) {
        roomViewModel.allDogs.observe(this) {
            adapter.submitList(it)
        }
    }


    private fun openCursorActivity() {
        val intent = Intent(this, CursorActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onChangedPrefs() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        when(prefs.getString(getString(R.string.db_selector_key),
        getString(R.string.selector_room_entry))) {
            getString(R.string.selector_cursor_entry) -> openCursorActivity()
            else -> findNavController(binding.navHostFragmentContainer.id).popBackStack()
        }
    }

    override fun insertDog(dog: Dog) {
        roomViewModel.insert(dog)
    }

    override fun updateDog(dog: Dog) {
        roomViewModel.update(dog)
    }
}