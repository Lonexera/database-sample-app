package com.databasesampleapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.databasesampleapp.databinding.ActivityMainBinding
import com.databasesampleapp.db.Repository

class RoomActivity : AppCompatActivity(), FragmentListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun getRepository(): Repository {
        return (application as DogsApplication).roomRepository as Repository
    }

    override fun switchActivity() {
        val intent = Intent(this, CursorActivity::class.java)
        startActivity(intent)
        finish()
    }
}
