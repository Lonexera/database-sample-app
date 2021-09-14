package com.databasesampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        when(prefs.getString(getString(R.string.selector_title),
            getString(R.string.selector_room_entry))) {
            getString(R.string.selector_room_entry) ->
                openRoomActivity()
            getString(R.string.selector_cursor_entry) ->
                openCursorActivity()
        }
    }

    private fun openRoomActivity() {
        val intent = Intent(this, RoomActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openCursorActivity() {
        val intent = Intent(this, CursorActivity::class.java)
        startActivity(intent)
        finish()
    }
}