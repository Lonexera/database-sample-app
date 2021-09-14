package com.databasesampleapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.databasesampleapp.db.cursor.CursorRepository
import com.databasesampleapp.db.room.Dog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DogCursorViewModel(private val repository: CursorRepository) : ViewModel() {

    val allDogs: Flow<List<Dog>> = repository.allDogs

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