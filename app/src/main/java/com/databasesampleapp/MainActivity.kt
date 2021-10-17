package com.databasesampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.databasesampleapp.viewModels.MainViewModel
import com.databasesampleapp.viewModels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.checkPrefs(this)

        with(viewModel) {
            toCursor.observe(this@MainActivity) {
                it.getContentIfNotHandled()?.let {
                    openActivity(CursorActivity::class.java)
                }
            }

            toRoom.observe(this@MainActivity) {
                it.getContentIfNotHandled()?.let {
                    openActivity(RoomActivity::class.java)
                }
            }
        }
    }

    private fun <T> openActivity(activityClass: Class<T>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
        finish()
    }
}
