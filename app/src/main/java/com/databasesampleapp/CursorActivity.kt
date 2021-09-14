package com.databasesampleapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.databasesampleapp.adapter.DogAdapter
import com.databasesampleapp.databinding.ActivityMainBinding
import com.databasesampleapp.view.ListFragment
import com.databasesampleapp.viewModels.DogCursorViewModel
import com.databasesampleapp.viewModels.DogCursorViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CursorActivity: AppCompatActivity(), ListFragment.ListFragmentListener {

    private lateinit var binding: ActivityMainBinding
    private val cursorViewModel: DogCursorViewModel by viewModels {
        DogCursorViewModelFactory((application as DogsApplication).cursorRepository)
    }
    private var adapter = DogAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                adapter.submitList(it)
            }
        }
    }

    private fun openRoomActivity() {
        val intent = Intent(this, RoomActivity::class.java)
        startActivity(intent)
        finish()
    }
}