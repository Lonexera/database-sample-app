package com.databasesampleapp.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.databasesampleapp.R
import com.databasesampleapp.utils.DB_SELECTOR_KEY
import com.databasesampleapp.utils.Event
import com.databasesampleapp.utils.ROOM

class MainViewModel : ViewModel() {

    private val _toRoom: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toRoom: LiveData<Event<Unit>> get() = _toRoom
    private val _toCursor: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toCursor: LiveData<Event<Unit>> get() = _toCursor
    private val _notSelected: MutableLiveData<Event<Unit>> = MutableLiveData()
    val notSelected: LiveData<Event<Unit>> get() = _notSelected

    fun checkPrefs(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        when (prefs.getString(
            DB_SELECTOR_KEY,
            ROOM
        )) {
            context.getString(R.string.selector_room_entry) -> {
                _toRoom.value = Event(Unit)
            }
            context.getString(R.string.selector_cursor_entry) -> {
                _toCursor.value = Event(Unit)
            }
            else -> {
                _notSelected.value = Event(Unit)
            }
        }
    }
}
