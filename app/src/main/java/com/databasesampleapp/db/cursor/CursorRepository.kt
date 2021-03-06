package com.databasesampleapp.db.cursor

import com.databasesampleapp.db.Repository
import com.databasesampleapp.db.room.Dog
import com.databasesampleapp.utils.CURSOR

class CursorRepository(
    private val dogCursorDao: DogCursorDao
) : Repository {

    override val repoName: String = CURSOR
    override val allDogs = dogCursorDao.getAll()

    override suspend fun insert(dog: Dog) {
        dogCursorDao.insert(dog)
    }

    override suspend fun update(dog: Dog) {
        dogCursorDao.update(dog)
    }

    override suspend fun delete(dog: Dog) {
        dogCursorDao.delete(dog)
    }
}
