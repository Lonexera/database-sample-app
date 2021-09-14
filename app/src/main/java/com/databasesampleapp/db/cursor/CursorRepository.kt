package com.databasesampleapp.db.cursor

import com.databasesampleapp.db.room.Dog

class CursorRepository(private val dogCursorDao: DogCursorDao) {

    val allDogs = dogCursorDao.getAll()

    suspend fun insert(dog: Dog) {
        dogCursorDao.insert(dog)
    }

    suspend fun update(dog: Dog) {
        dogCursorDao.update(dog)
    }

    suspend fun delete(dog: Dog) {
        dogCursorDao.delete(dog)
    }
}