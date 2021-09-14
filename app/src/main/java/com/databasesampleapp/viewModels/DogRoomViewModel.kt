package com.databasesampleapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.databasesampleapp.db.room.Dog
import com.databasesampleapp.db.room.RoomRepository
import kotlinx.coroutines.launch

class DogRoomViewModel(private val repository: RoomRepository) : ViewModel() {

    val allDogs: LiveData<List<Dog>> = repository.allDogs.asLiveData()

    fun insert(dog: Dog) = viewModelScope.launch {
        repository.insert(dog)
    }

    fun update(dog: Dog) = viewModelScope.launch {
        repository.update(dog)
    }

    fun delete(dog: Dog) = viewModelScope.launch {
        repository.delete(dog)
    }
}