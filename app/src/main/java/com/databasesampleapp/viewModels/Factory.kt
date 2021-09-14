package com.databasesampleapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.databasesampleapp.db.cursor.CursorRepository
import com.databasesampleapp.db.room.RoomRepository


class DogRoomViewModelFactory(private val repository: RoomRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DogRoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DogRoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}


class DogCursorViewModelFactory(private val repository: CursorRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DogCursorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DogCursorViewModel(repository) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel Class")
    }
}