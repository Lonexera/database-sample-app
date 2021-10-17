package com.databasesampleapp.viewModels

import android.content.Context
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.databasesampleapp.db.Repository
import com.databasesampleapp.db.room.Dog
import com.databasesampleapp.utils.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _filteredDogs: MutableLiveData<List<Dog>> = MutableLiveData()

    private val _toList: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toList: LiveData<Event<Unit>> get() = _toList

    private val _toAdd: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toAdd: LiveData<Event<Unit>> get() = _toAdd

    private val _toSwitchActivity: MutableLiveData<Event<Unit>> = MutableLiveData()
    val toSwitchActivity: LiveData<Event<Unit>> get() = _toSwitchActivity

    private val _dogToUpdate: MutableLiveData<Event<Dog>> = MutableLiveData()
    val dogToUpdate: LiveData<Event<Dog>> get() = _dogToUpdate

    fun getFilteredDogs(context: Context): LiveData<List<Dog>> {
        filterList(context)
        return _filteredDogs
    }

    fun onDelete(dog: Dog, context: Context) = viewModelScope.launch {
        repository.delete(dog)
        filterList(context)
    }

    fun onEdit(dog: Dog) {
        _dogToUpdate.value = Event(dog)
    }

    fun onAddClick() {
        _toAdd.value = Event(Unit)
    }

    fun onChangedPrefs(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        val repoImpl = repository.repoName

        when (prefs.getString(DB_SELECTOR_KEY, repoImpl)) {
            repoImpl -> _toList.value = Event(Unit)
            else -> _toSwitchActivity.value = Event(Unit)
        }
    }

    private fun filterList(context: Context) {
        viewModelScope.launch {
            repository.allDogs.collect { dogs ->
                var filteredList: List<Dog> = dogs

                val prefs = PreferenceManager.getDefaultSharedPreferences(context)

                val defaultValue = FILTER_DEFAULT_VALUE
                val prefName = prefs?.getString(
                    FILTER_NAME_KEY,
                    defaultValue
                ) ?: defaultValue

                val prefAge = prefs?.getString(
                    FILTER_AGE_KEY,
                    defaultValue
                ) ?: defaultValue

                val prefBreed = prefs?.getString(
                    FILTER_BREED_KEY,
                    defaultValue
                ) ?: defaultValue

                filteredList =
                    if (prefName != defaultValue)
                        filteredList.filter { it.name == prefName }
                    else filteredList

                filteredList = if (prefAge != defaultValue &&
                    prefAge.matches("""[0-9]{1,3}""".toRegex())
                ) {
                    filteredList.filter { it.age == prefAge.toInt() }
                } else filteredList

                filteredList = if (prefBreed != defaultValue)
                    filteredList.filter { it.breed == prefBreed }
                else filteredList

                _filteredDogs.value = filteredList
            }
        }
    }
}
