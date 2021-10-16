package com.databasesampleapp.db

import com.databasesampleapp.db.room.Dog
import kotlinx.coroutines.flow.Flow

interface Repository {
    val allDogs: Flow<List<Dog>>

    suspend fun insert(dog: Dog)
    suspend fun update(dog: Dog)
    suspend fun delete(dog: Dog)
}
