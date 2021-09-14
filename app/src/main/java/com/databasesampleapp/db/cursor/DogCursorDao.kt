package com.databasesampleapp.db.cursor

import com.databasesampleapp.db.room.Dog
import kotlinx.coroutines.flow.Flow

interface DogCursorDao {

    suspend fun insert(dog: Dog)
    suspend fun update(dog: Dog)
    suspend fun delete(dog: Dog)
    fun getAll(): Flow<List<Dog>>
}