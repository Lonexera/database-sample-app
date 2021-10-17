package com.databasesampleapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.databasesampleapp.databinding.ActivityMainBinding
import com.databasesampleapp.db.Repository
import com.databasesampleapp.view.FragmentListener
import com.databasesampleapp.viewModels.DogViewModelFactory
import com.databasesampleapp.viewModels.ListViewModel

class RoomActivity : AppCompatActivity(), FragmentListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ListViewModel by viewModels {
        DogViewModelFactory(getRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.toSwitchActivity.observe(this) {
            it.getContentIfNotHandled()?.let {
                switchActivity()
            }
        }
    }

    override fun getRepository(): Repository {
        return (application as DogsApplication).roomRepository
    }

    override fun switchActivity() {
        val intent = Intent(this, CursorActivity::class.java)
        startActivity(intent)
        finish()
    }
}
