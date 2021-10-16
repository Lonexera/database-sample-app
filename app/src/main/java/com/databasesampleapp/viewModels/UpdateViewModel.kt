package com.databasesampleapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.databasesampleapp.db.Repository
import com.databasesampleapp.db.room.Dog
import com.databasesampleapp.utils.Event
import kotlinx.coroutines.launch

class UpdateViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _inputNameError: MutableLiveData<Event<Unit>> = MutableLiveData()
    val inputNameError: LiveData<Event<Unit>> get() = _inputNameError

    private val _inputAgeError: MutableLiveData<Event<Unit>> = MutableLiveData()
    val inputAgeError: LiveData<Event<Unit>> get() = _inputAgeError

    private val _inputBreedError: MutableLiveData<Event<Unit>> = MutableLiveData()
    val inputBreedError: LiveData<Event<Unit>> get() = _inputBreedError

    private val _inputError: MutableLiveData<Event<Unit>> = MutableLiveData()
    val inputError: LiveData<Event<Unit>> get() = _inputError

    private val _toList: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toList: LiveData<Event<Unit>> get() = _toList


    fun onAddBtnClicked(
        dogName: String,
        dogAge: String,
        dogBreed: String
    ) {
        if (isDataValid(dogName, dogAge, dogBreed)) {
            insertDog(
                Dog(
                    dogName,
                    dogAge.toInt(),
                    dogBreed
                )
            )
        } else {
            _inputError.value = Event(Unit)
        }
    }

    fun onUpdateBtnClicked(
        dogName: String,
        dogAge: String,
        dogBreed: String,
        dogId: Int
    ) {
        if (isDataValid(dogName, dogAge, dogBreed)) {
            insertDog(
                Dog(
                    dogName,
                    dogAge.toInt(),
                    dogBreed
                ).apply { uid = dogId }
            )
        } else {
            _inputError.value = Event(Unit)
        }
    }

    fun onBack() {
        _toList.value = Event(Unit)
    }

    private fun insertDog(dog: Dog) = viewModelScope.launch {
        repository.insert(dog)
    }

    private fun updateDog(dog: Dog) = viewModelScope.launch {
        repository.update(dog)
    }

    private fun isDataValid(
        dogName: String,
        dogAge: String,
        dogBreed: String
    ): Boolean {
        return when {
            !dogName.matches(""".+""".toRegex()) -> {
                _inputNameError.value = Event(Unit)
                false
            }
            !dogAge.matches("""[0-9]{1,3}""".toRegex()) -> {
                _inputAgeError.value = Event(Unit)
                false
            }
            !dogBreed.matches(""".+""".toRegex()) -> {
                _inputBreedError.value = Event(Unit)
                false
            }
            else -> true
        }
    }
}
